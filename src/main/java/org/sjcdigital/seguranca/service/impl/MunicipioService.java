package org.sjcdigital.seguranca.service.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.sjcdigital.seguranca.model.Municipio;
import org.sjcdigital.seguranca.service.Service;

public class MunicipioService extends Service<Municipio> {

	public Municipio buscaPorNome(String nome) {
		TypedQuery<Municipio> q = em.createNamedQuery("Municipio.porNome", Municipio.class);
		q.setParameter("nome", nome);
		return q.getSingleResult();
	}

	public List<Municipio> porSiglaEstado(String sigla) {
		TypedQuery<Municipio> q = em.createNamedQuery("Municipio.porSiglaEstado", Municipio.class);
		q.setParameter("sigla", sigla);
		return q.getResultList();
	}

}
