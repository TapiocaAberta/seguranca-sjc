package org.sjcdigital.seguranca.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.sjcdigital.seguranca.model.Delegacia;
import org.sjcdigital.seguranca.model.Natureza;
import org.sjcdigital.seguranca.model.Ocorrencia;
import org.sjcdigital.seguranca.service.Service;
import org.sjcdigital.seguranca.utils.DataUtils;

public class OcorrenciaService extends Service<Ocorrencia> {
	
	public boolean verificaSeJaExiste(Delegacia delegacia, Natureza natureza, Date data) {
		TypedQuery<Long> q =  em.createNamedQuery("Ocorrencia.contaPorDelNatAnoEMes", Long.class);
		q.setParameter("natureza", natureza);
		q.setParameter("delegacia", delegacia);
		q.setParameter("data", data);
		return q.getSingleResult() > 0;
	}

	public List<Ocorrencia> ocorrenciasPorMunicipioDpAnoMes(long idMunicipio,
			long idDp, Date data) {
		TypedQuery<Ocorrencia> q = em.createNamedQuery("Ocorrencia.porMunicipioDPAnoMes", Ocorrencia.class);	
		q.setParameter("idMunicipio", idMunicipio);
		q.setParameter("idDp", idDp);
		q.setParameter("data", data);
		return q.getResultList();
	}

	public List<Ocorrencia> ocorrenciasPorMunicipioNaturezaAnoMes(
			long idMunicipio, long idNatureza, Date data) {
		TypedQuery<Ocorrencia> q = em.createNamedQuery("Ocorrencia.porMunicipioNaturezaAnoMes", Ocorrencia.class);	
		q.setParameter("idMunicipio", idMunicipio);
		q.setParameter("idNatureza", idNatureza);
		q.setParameter("data", data);
		return q.getResultList();
	}

	public List<Ocorrencia> ocorrenciasPorMunicipioAnoMes(
			long idMunicipio, Date data) {
		TypedQuery<Ocorrencia> q = em.createNamedQuery("Ocorrencia.porMunicipioAnoMes", Ocorrencia.class);	
		q.setParameter("idMunicipio", idMunicipio);
		q.setParameter("data", data);
		return q.getResultList();
	}

	public List<Ocorrencia> ocorrenciasPorMunicipioNaturezaAnoMes(
			long idMunicipio, List<Long> dps, long idNatureza, Date data) {
		TypedQuery<Ocorrencia> q = em.createNamedQuery("Ocorrencia.porMunicipioDpsNaturezaAnoMes", Ocorrencia.class);	
		q.setParameter("idMunicipio", idMunicipio);
		q.setParameter("idNatureza", idNatureza);
		q.setParameter("dps", dps);
		q.setParameter("data", data);
		return q.getResultList();
	}

	public List<Ocorrencia> ocorrenciasPorMunicipioDpAno(long idMunicipio, long idDp, int ano) {
		TypedQuery<Ocorrencia> q = em.createNamedQuery("Ocorrencia.porMunicipioDPAno", Ocorrencia.class);	
		q.setParameter("idMunicipio", idMunicipio);
		q.setParameter("idDp", idDp);
		// para evitar funções proprietárias na named query
		q.setParameter("dataInicio", DataUtils.anoMes(ano, 1));
		q.setParameter("dataFim", DataUtils.anoMes(ano, 12));
		return q.getResultList();
	}

	public List<Ocorrencia> ocorrenciasPorMunicipioNaturezaAno(
			long idMunicipio, long idNatureza, int ano) {
		TypedQuery<Ocorrencia> q = em.createNamedQuery("Ocorrencia.porMunicipioNaturezaAno", Ocorrencia.class);	
		q.setParameter("idMunicipio", idMunicipio);
		q.setParameter("idNatureza", idNatureza);
		// para evitar funções proprietárias na named query
		q.setParameter("dataInicio", DataUtils.anoMes(ano, 1));
		q.setParameter("dataFim", DataUtils.anoMes(ano, 12));
		return q.getResultList();
	}

}