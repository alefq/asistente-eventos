package py.gov.senatics.asistente.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the PAIS database table.
 * 
 */
@Entity
@Table(name = "PAIS")
public class Pais implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PAIS_PAISID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAIS_PAISID_GENERATOR")
	@Column(name = "PAIS_ID")
	private long paisId;

	@Column(name = "CODIGO_ISO")
	private String codigoIso;

	private String nombre;

	// bi-directional many-to-one association to Ciudad
	@OneToMany(mappedBy = "pais")
	private Set<Ciudad> ciudads;

	public Pais() {

	}

	public Pais(String nombre) {

		super();
		this.nombre = nombre;
	}

	public long getPaisId() {

		return this.paisId;
	}

	public void setPaisId(long paisId) {

		this.paisId = paisId;
	}

	public String getCodigoIso() {

		return this.codigoIso;
	}

	public void setCodigoIso(String codigoIso) {

		this.codigoIso = codigoIso;
	}

	public String getNombre() {

		return this.nombre;
	}

	public void setNombre(String nombre) {

		this.nombre = nombre;
	}

	public Set<Ciudad> getCiudads() {

		return this.ciudads;
	}

	public void setCiudads(Set<Ciudad> ciudads) {

		this.ciudads = ciudads;
	}

	/** Para el convert py.gov.senatics.asistente.converter.paisConverter **/
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		if (paisId != other.paisId)
			return false;
		return true;
	}

}
