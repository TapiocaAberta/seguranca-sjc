package org.sjcdigital.seguranca.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@XmlRootElement
@Entity
@Table(name = "natureza")
@Cacheable
@NamedQueries({ @NamedQuery(name = "Natureza.porNome", query = "select n from Natureza n where n.nome like :nome") })
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "cache-classes-basicas")
public class Natureza {

	@Id
	@Column(name = "nat_id")
	private long id;

	@Column(name = "nat_nome")
	private String nome;

	@Column(name = "nat_observacao")
	private String observacao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
