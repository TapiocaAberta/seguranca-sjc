package org.sjcdigital.seguranca.load;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.sjcdigital.seguranca.model.Delegacia;
import org.sjcdigital.seguranca.model.Estado;
import org.sjcdigital.seguranca.model.Municipio;
import org.sjcdigital.seguranca.model.Natureza;
import org.sjcdigital.seguranca.service.impl.DelegaciaService;
import org.sjcdigital.seguranca.service.impl.EstadoService;
import org.sjcdigital.seguranca.service.impl.MunicipioService;
import org.sjcdigital.seguranca.service.impl.NaturezaService;

/**
 * 
 * Realiza a carga de dados para a memória do servidor (serão mantidos em
 * memória)
 * 
 * @author wsiqueir
 *
 */
@Singleton
public class CargaBase {

	private final String ARQUIVO_BASE_DADOS = "/dados/base/dadosBase.xml";
	
	@Inject
	private Logger logger;

	@Inject
	private DelegaciaService delegaciaService;

	@Inject
	private NaturezaService naturezaService;

	@Inject
	private MunicipioService municipioService;

	@Inject
	private EstadoService estadoService;


	public void carregaDadosBase() throws IOException {
		logger.info("Iniciando carga de arquivo de dados base");
		DadosBase base = JAXB.unmarshal(
				this.getClass().getResourceAsStream(ARQUIVO_BASE_DADOS),
				DadosBase.class);
		int delegacias = base.getDelegacias().size();
		int naturezas = base.getNaturezas().size();
		logger.info("Salvando dados(" + delegacias + " delegacias, " + naturezas + " naturezas)");
		base.getEstados().forEach(estadoService::salvar);
		base.getMunicipios().forEach(this::salvaMunicipio);
		base.getDelegacias().stream().forEach(this::salvaDelegacia);
		base.getNaturezas().stream().forEach(naturezaService::salvar);
		logger.info("Fim carga dados base");
	}

	public void salvaMunicipio(Municipio m) {
		long eId = m.getEstado().getId();
		Estado e = estadoService.buscaPorIdOuCria(eId, m::getEstado);
		m.setEstado(e);
		municipioService.salvar(m);
	}

	/**
	 * Salva a delegacia e os dados relacionados. Dados manuais no arquivo XML deverão ter ID. Futuramente separar carga de municipios
	 * 
	 * @param d
	 */
	private void salvaDelegacia(Delegacia d) {
		Municipio m = d.getMunicipio();
		long mId = m.getId();
		m = municipioService.buscaPorIdOuCria(mId, d::getMunicipio);
		d.setMunicipio(m);
		delegaciaService.salvar(d);
	}

	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class DadosBase {
		
		@XmlElement(name = "municipio", type = Municipio.class)
		@XmlElementWrapper(name = "municipios")
		List<Municipio> municipios;

		@XmlElement(name = "estado", type = Estado.class)
		@XmlElementWrapper(name = "estados")
		List<Estado> estados;
		
		@XmlElement(name = "delegacia", type = Delegacia.class)
		@XmlElementWrapper(name = "delegacias")
		List<Delegacia> delegacias;

		@XmlElement(name = "natureza", type = Natureza.class)
		@XmlElementWrapper(name = "naturezas")
		List<Natureza> naturezas;

		public List<Delegacia> getDelegacias() {
			return delegacias;
		}

		public void setDelegacias(List<Delegacia> delegacias) {
			this.delegacias = delegacias;
		}

		public List<Natureza> getNaturezas() {
			return naturezas;
		}

		public void setNaturezas(List<Natureza> naturezas) {
			this.naturezas = naturezas;
		}

		public List<Municipio> getMunicipios() {
			return municipios;
		}

		public void setMunicipios(List<Municipio> municipios) {
			this.municipios = municipios;
		}

		public List<Estado> getEstados() {
			return estados;
		}

		public void setEstados(List<Estado> estados) {
			this.estados = estados;
		}
		
	}
}