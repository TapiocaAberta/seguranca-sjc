package org.sjcdigital.seguranca.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.sjcdigital.seguranca.model.Ocorrencia;
import org.sjcdigital.seguranca.service.impl.OcorrenciaService;
import org.sjcdigital.seguranca.utils.DataUtils;


@Path("ocorrencia/municipio/{idMunicipio}")
@Produces("application/json;charset=utf-8")
public class OcorrenciaResource {	
		
	@PathParam("idMunicipio")
	long idMunicipio;
	
	@Inject
	OcorrenciaService ocorrenciaService;
	
	/**
	 * 
	 * Retorna a ocorrência para cada natureza para a dada DP, municipio, ano e mês
	 * @return 
	 * 	A lista de ocorrências para cada natureza para os parâmetros passados
	 */
	@GET
	@Path("delegacia/{idDp}/data/{ano}/{mes}")
	public List<Ocorrencia> ocorrenciasPorMunicipioDpAnoMes(@PathParam("idDp") long idDp, @PathParam("ano") int ano, @PathParam("mes") int mes) {
		return ocorrenciaService.ocorrenciasPorMunicipioDpAnoMes(idMunicipio, idDp, DataUtils.anoMes(ano, mes));
	}
	
	/**
	 * 
	 * Retorna a ocorrência para cada natureza para a dada DP, municipio, ano e mês
	 * @return 
	 * 	A lista de ocorrências para cada natureza para os parâmetros passados
	 */
	@GET
	@Path("delegacia/{idDp}/data/{ano}/")
	public List<Ocorrencia> ocorrenciasPorMunicipioDpsAno(@PathParam("idDp") long idDp, @PathParam("ano") int ano) {
		return ocorrenciaService.ocorrenciasPorMunicipioDpAno(idMunicipio, idDp, ano);
	}
	
	/**
	 * 
	 * Retorna todas as ocorrências para o município passado. O cliente deve organizar os dados
	 * @return 
	 * 	Lista de todas as ocorrências para o ano, munípio e mês
	 */
	@Path("data/{ano}/{mes}/")
	@GET
	public List<Ocorrencia> ocorrenciasPorMunicipioAnoMes(@PathParam("ano") int ano, @PathParam("mes") int mes) {
		return ocorrenciaService.ocorrenciasPorMunicipioAnoMes(idMunicipio, DataUtils.anoMes(ano, mes));
	}
	
	
	/**
	 * 
	 * Retorna todas as ocorrências para o município passado e as delegacias. O cliente deve organizar os dados(inverter para agrupar por delegacia).
	 * @return 
	 * 	Lista de todas as ocorrências para o ano, munípio e mês
	 */
	@Path("delegacia/{delegacias}/data/{ano}/{mes}/")
	@GET
	public List<Ocorrencia> ocorrenciasPorMunicipioDpsAnoMes(@PathParam("delegacias") String delegacias, @PathParam("ano") int ano, @PathParam("mes") int mes) {
		String[] ids = delegacias.split("\\,");
		Date data = DataUtils.anoMes(ano, mes);
		List<Ocorrencia> retorno = new ArrayList<>();
		Stream.of(ids).map(Long::parseLong).forEach(id -> {
			retorno.addAll(ocorrenciaService.ocorrenciasPorMunicipioDpAnoMes(idMunicipio, id, data));
		});
		return retorno;
	}
	
	/**
	 * 
	 * Retorna a ocorrência para cada DP naquele ano e mês do município passado
	 * @return 
	 * 	A lista de ocorrências para cada natureza para os parâmetros passados
	 */
	@GET
	@Path("natureza/{idNatureza}/data/{ano}/{mes}")
	public List<Ocorrencia> ocorrenciasPorMunicipioNaturezaAnoMes(@PathParam("idNatureza") long idNatureza, @PathParam("ano") int ano, @PathParam("mes") int mes) {
		return ocorrenciaService.ocorrenciasPorMunicipioNaturezaAnoMes(idMunicipio, idNatureza, DataUtils.anoMes(ano, mes));
	}
	
	/**
	 * 
	 * Retorna a ocorrência para cada DP naquele ano e mês do município passado
	 * @return 
	 * 	A lista de ocorrências para cada natureza para os parâmetros passados
	 */
	@GET
	@Path("natureza/{idNatureza}/data/{ano}")
	public List<Ocorrencia> ocorrenciasPorMunicipioNaturezaAno(@PathParam("idNatureza") long idNatureza, @PathParam("ano") int ano) {
		return ocorrenciaService.ocorrenciasPorMunicipioNaturezaAno(idMunicipio, idNatureza, ano);
	}
	
	/**
	 * 
	 * Retorna a ocorrência para cada DP naquele ano e mês do município passado
	 * @return 
	 * 	A lista de ocorrências para cada natureza para os parâmetros passados
	 */
	@GET
	@Path("delegacias/{delegacias}/natureza/{idNatureza}/data/{ano}/{mes}")
	public List<Ocorrencia> ocorrenciasPorMunicipioNaturezaDpsAnoMes(@PathParam("delegacias") String delegacias, @PathParam("idNatureza") long idNatureza, @PathParam("ano") int ano, @PathParam("mes") int mes) {
		List<Long> dps = Stream.of(delegacias.split("\\,")).map(Long::parseLong).collect(Collectors.toList());
		return ocorrenciaService.ocorrenciasPorMunicipioNaturezaAnoMes(idMunicipio, dps, idNatureza, DataUtils.anoMes(ano, mes));
	}	
	
}