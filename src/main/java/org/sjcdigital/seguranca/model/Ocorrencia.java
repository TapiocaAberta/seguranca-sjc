package org.sjcdigital.seguranca.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name = "ocorrencia")
@Cacheable
@NamedQueries({
		@NamedQuery(name = "Ocorrencia.contaPorDelNatAnoEMes", query = "SELECT count(o) FROM Ocorrencia o WHERE o.delegacia = :delegacia AND o.natureza = :natureza AND o.data = :data"),
		@NamedQuery(name = "Ocorrencia.porMunicipioDPAnoMes", query = "SELECT o from Ocorrencia o where o.delegacia.id = :idDp and o.delegacia.municipio.id = :idMunicipio and o.data = :data"),
		@NamedQuery(name = "Ocorrencia.porMunicipioDPAno", query = "SELECT o from Ocorrencia o where o.delegacia.id = :idDp and o.delegacia.municipio.id = :idMunicipio and o.data >= :dataInicio and o.data <= :dataFim order by o.data"),
		@NamedQuery(name = "Ocorrencia.porMunicipioAnoMes", query = "SELECT o from Ocorrencia o where o.delegacia.municipio.id = :idMunicipio and o.data = :data"),
		@NamedQuery(name = "Ocorrencia.porMunicipioNaturezaAnoMes", query = "SELECT o from Ocorrencia o where o.natureza.id = :idNatureza and o.delegacia.municipio.id = :idMunicipio and o.data = :data"),
		@NamedQuery(name = "Ocorrencia.porMunicipioDpsNaturezaAnoMes", query = "SELECT o from Ocorrencia o WHERE o.delegacia.id IN :dps AND o.natureza.id = :idNatureza and o.delegacia.municipio.id = :idMunicipio and o.data = :data"),
		@NamedQuery(name = "Ocorrencia.porMunicipioNaturezaAno", query = "SELECT o from Ocorrencia o WHERE o.natureza.id = :idNatureza and o.delegacia.municipio.id = :idMunicipio and o.data >= :dataInicio and o.data <= :dataFim order by o.data"),

})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "cache-classes-basicas")
public class Ocorrencia {

	@Id
	@GeneratedValue
	@Column(name = "oco_id")
	private long id;

	@Column(name = "oco_data")
	private Date data;

	@Column(name = "oco_total")
	private long total;

	@ManyToOne
	@JoinColumn(name = "delegacia_del_id")
	private Delegacia delegacia;

	@ManyToOne
	@JoinColumn(name = "natureza_nat_id")
	private Natureza natureza;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Delegacia getDelegacia() {
		return delegacia;
	}

	public void setDelegacia(Delegacia delegacia) {
		this.delegacia = delegacia;
	}

	public Natureza getNatureza() {
		return natureza;
	}

	public void setNatureza(Natureza natureza) {
		this.natureza = natureza;
	}

}
