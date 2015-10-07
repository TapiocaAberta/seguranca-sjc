package org.sjcdigital.seguranca.service.impl;

import javax.persistence.TypedQuery;

import org.sjcdigital.seguranca.model.Natureza;
import org.sjcdigital.seguranca.service.Service;

public class NaturezaService extends Service<Natureza> {
	
	public Natureza buscarPorNome(String nome) {
		TypedQuery<Natureza> q =  em.createNamedQuery("Natureza.porNome", Natureza.class);
		q.setParameter("nome", nome);
		return q.getSingleResult();
	}

}
