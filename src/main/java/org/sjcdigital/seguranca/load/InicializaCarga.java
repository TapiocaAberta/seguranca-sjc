package org.sjcdigital.seguranca.load;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;


/**
 * 
 * Realiza a carga de dados para a memória do servidor (serão mantidos em
 * memória)
 * 
 * @author wsiqueir
 *
 */
@Singleton
@Startup
public class InicializaCarga {
	
	@Inject
	CargaBase cargaBase;
	@Inject
	CargaOcorrencia cargaOcorrencia;
	
	@PostConstruct
	void carrega() throws IOException {
		cargaBase.carregaDadosBase();
		cargaOcorrencia.escaneiaECarrega();
	}

}
