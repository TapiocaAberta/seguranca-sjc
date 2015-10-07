package org.sjcdigital.seguranca.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.sjcdigital.seguranca.model.Delegacia;
import org.sjcdigital.seguranca.model.Municipio;
import org.sjcdigital.seguranca.model.Natureza;
import org.sjcdigital.seguranca.service.impl.DelegaciaService;
import org.sjcdigital.seguranca.service.impl.MunicipioService;
import org.sjcdigital.seguranca.service.impl.NaturezaService;

@Path("")
@Produces("application/json;charset=utf-8")
public class MetaResource {

	@Inject
	DelegaciaService delegaciaService;

	@Inject
	NaturezaService naturezaService;

	@Inject
	MunicipioService municipioService;

	@Path("municipio/{idMunicipio}/delegacias")
	@GET
	public List<Delegacia> delegacias(@PathParam("idMunicipio") long idMunicipio) {
		return delegaciaService.delegaciasPorMunicipio(idMunicipio);
	}

	@Path("anos")
	@GET
	public List<Long> anos() {
		// TODO
		return null;
	}

	@Path("naturezas")
	@GET
	public List<Natureza> naturezas() {
		return naturezaService.todos();
	}

	@Path("/estado/{sigla: [A-z][A-z]}/municipios")
	@GET
	public List<Municipio> municipios(@PathParam("sigla") String sigla) {
		return municipioService.porSiglaEstado(sigla.toUpperCase());
	}

}