package org.sjcdigital.seguranca.resources;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

/**
 * 
 * Realiza os testes do MetaResource. <br />
 * Necessita ter o JBoss rodando localmente para testes
 * 
 * @author wsiqueir
 *
 */
public class MetaResourceTest {

	private static final int TOTAL_NATUREZAS = 19;

	private static final int TOTAL_DELEGACIAS_SJC = 12;

	final String BASE_URI = "http://localhost:8080/seguranca-sjc/rest";
	
	final int ID_MUNICIPIO = 1;

	@Before
	public void inicializa() {
		RestAssured.baseURI = BASE_URI;
	}

	@Test
	public void municipio() {
		get("/estado/SP/municipios").then().body("[0].id", equalTo(ID_MUNICIPIO));
	}
	
	@Test
	public void naturezas() {
		get("/naturezas").then().body("size()", equalTo(TOTAL_NATUREZAS));
	}
	
	@Test
	public void delegacias() {
		get("/municipio/1/delegacias").then().body("size()", equalTo(TOTAL_DELEGACIAS_SJC));
	}

}
