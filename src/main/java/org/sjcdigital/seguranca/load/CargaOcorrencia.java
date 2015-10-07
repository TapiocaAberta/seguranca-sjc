package org.sjcdigital.seguranca.load;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.sjcdigital.seguranca.model.Delegacia;
import org.sjcdigital.seguranca.model.Natureza;
import org.sjcdigital.seguranca.model.Ocorrencia;
import org.sjcdigital.seguranca.service.impl.DelegaciaService;
import org.sjcdigital.seguranca.service.impl.EstadoService;
import org.sjcdigital.seguranca.service.impl.MunicipioService;
import org.sjcdigital.seguranca.service.impl.NaturezaService;
import org.sjcdigital.seguranca.service.impl.OcorrenciaService;
import org.sjcdigital.seguranca.utils.DataUtils;

@Singleton
public class CargaOcorrencia {
	
	@Inject
	private Logger logger;

	private String ARQUIVO_NOMES_MUNICIPIOS = "/dados/ajuste/ajusteNomeMunicipios.properties";
	final String DIRETORIO_CSVS = "/dados/csvs";

	Properties nomesMunicipios;

	@Inject
	DelegaciaService delegaciaService;

	@Inject
	NaturezaService naturezaService;

	@Inject
	MunicipioService municipioService;

	@Inject
	EstadoService estadoService;

	@Inject
	OcorrenciaService ocorrenciaService;

	@PostConstruct
	void carregaProperties() throws IOException {
		nomesMunicipios = new Properties();
		nomesMunicipios.load(getClass().getResourceAsStream(
				ARQUIVO_NOMES_MUNICIPIOS));

	}

	public void escaneiaECarrega() {
		logger.info("Iniciando carga dos CSVs da SSP...");
		logger.info("Escaneando CSVs");
		try {
			String file = getClass().getResource(DIRETORIO_CSVS).getFile();
			Files.walk(Paths.get(file))
					.filter(f -> f.getFileName().toString().endsWith(".csv"))
					.forEach(this::salvaCSV);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	/**
	 * 
	 * Carrega o caminho do CSV e salva os dados no banco
	 * 
	 * @param p
	 */
	public void salvaCSV(Path p) {
		logger.info("Salvando arquivo: " + p);
		String nome[] = p.getFileName().toString().replaceAll(".csv", "")
				.split("\\-");
		// remove o caracter em branco no final
		String nomeDelegacia = nome[1].substring(0, nome[1].length() - 1);
		String nomeMunicipio =  nomesMunicipios.getProperty(nome[2].substring(1));
		Delegacia delegacia = delegaciaService.buscaPorNomeSSPEMunicipio(
				nomeDelegacia, nomeMunicipio);
		AtomicInteger anoAtual = new AtomicInteger();
		AtomicBoolean mudouAno = new AtomicBoolean();
		try {
			Files.lines(p, StandardCharsets.ISO_8859_1).forEach(
					l -> {
						if(l.trim().isEmpty()) return;
						boolean linhaComAno = Pattern.matches("[0-9][0-9][0-9][0-9]", l.trim());
						if (linhaComAno) {
							anoAtual.set(Integer.parseInt(l));
							mudouAno.set(true);
						} else if (mudouAno.get()) {
							mudouAno.set(false);
						} else {
							String[] colunas = l.split("\\;");
							Natureza natureza = naturezaService.buscarPorNome(colunas[0]);
							for (int i = 1; i < 13; i++) {
								if(colunas[i].equals("...")) continue;
								Ocorrencia o = new Ocorrencia();
								int mes = i;
								int ano = anoAtual.get();
								long total = Long.valueOf(colunas[i]);
								Date data = DataUtils.anoMes(ano, mes);
								boolean jaInserido = ocorrenciaService.verificaSeJaExiste(delegacia, natureza, data);
								if (!jaInserido) {
									o.setNatureza(natureza);
									o.setDelegacia(delegacia);
									o.setTotal(total);
									o.setData(data);
									ocorrenciaService.salvar(o);
								}
							}
						}

					});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}