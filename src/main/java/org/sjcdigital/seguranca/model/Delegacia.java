package org.sjcdigital.seguranca.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@XmlRootElement
@Entity
@Table(name = "delegacia")
@NamedQueries({ @NamedQuery(name = "Delegacia.porMunicipio", query = "select d from Delegacia d where d.municipio.id = :municipioId"),
	@NamedQuery(name = "Delegacia.porSSPEMunicipio", query = "select d from Delegacia d where d.nomeSSP = :nomeSSP AND d.municipio.nome = :nomeMunicipio")
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "cache-classes-basicas")
public class Delegacia {

	@Id
	@Column(name = "del_id")
	private long id;

	@Column(name = "del_nome")
	private String nome;

	@Column(name = "del_nome_ssp")
	private String nomeSSP;

	@Column(name = "del_endereco")
	private String endereco;

	@Column(name = "del_telefone")
	private String telefone;

	@ManyToOne
	@JoinColumn(name = "municipio_mun_id")
	private Municipio municipio;

	@Override
	public String toString() {
		return nome + " - " + nomeSSP + " - " + municipio;
	}

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

	public String getNomeSSP() {
		return nomeSSP;
	}

	public void setNomeSSP(String nomeSSP) {
		this.nomeSSP = nomeSSP;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

}