package org.sjcdigital.seguranca.service.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.sjcdigital.seguranca.model.Delegacia;
import org.sjcdigital.seguranca.service.Service;

public class DelegaciaService extends Service<Delegacia> {
	
	public List<Delegacia> delegaciasPorMunicipio(long municipioId) {
		TypedQuery<Delegacia> q = em.createNamedQuery("Delegacia.porMunicipio", Delegacia.class);
		q.setParameter("municipioId", municipioId);
		return q.getResultList();
	}

	public Delegacia buscaPorNomeSSPEMunicipio(String nomeSSP, String nomeMunicipio) {
		TypedQuery<Delegacia> q = em.createNamedQuery("Delegacia.porSSPEMunicipio", Delegacia.class);
		q.setParameter("nomeMunicipio", nomeMunicipio);
		q.setParameter("nomeSSP", nomeSSP);
		return q.getSingleResult();
	}
}
