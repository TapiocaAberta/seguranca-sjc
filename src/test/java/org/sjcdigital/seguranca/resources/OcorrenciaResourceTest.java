package org.sjcdigital.seguranca.resources;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

/**
 * 
 * Testes para os endpoints da ocorrência. Ver no CSV e testar aqui.
 * 
 * @author wsiqueir
 *
 */
public class OcorrenciaResourceTest {
	
	
	private static final int TOTAL_DOLOSO_ANO_2015_MES_2_DELEGACIA_8 = 1;
	private static final int TOTAL_FURTOS_OUTROS_ANO_2013_MES_12_DELEGACIA_7 = 59;


	final String BASE_URI = "http://localhost:8080/seguranca-sjc/rest";
	
	final int ID_MUNICIPIO = 1;

	@Before
	public void inicializa() {
		RestAssured.baseURI = BASE_URI;
	}
	
	@Test
	public void testaOcorrenciasPorMunicipioDpAnoMes() {
		get("/ocorrencia/municipio/1/delegacia/8/data/2015/2").then().body("[0].total", equalTo(TOTAL_DOLOSO_ANO_2015_MES_2_DELEGACIA_8));
		get("ocorrencia/municipio/1/delegacia/7/data/2013/12").then().body("[17].total", equalTo(TOTAL_FURTOS_OUTROS_ANO_2013_MES_12_DELEGACIA_7));
	}

}
