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


/**
 * The persistent class for the INSTITUCION database table.
 * 
 */
@Entity
public class Institucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INSTITUCION_INSTITUCIONID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSTITUCION_INSTITUCIONID_GENERATOR")
	@Column(name="INSTITUCION_ID")
	private long institucionId;

	private String nombre;

	private String observaciones;

	private String ruc;

	private String tipo;

	//bi-directional many-to-one association to Persona
	@OneToMany(mappedBy="institucion")
	private Set<Persona> personas;

    public Institucion() {
    }

	public long getInstitucionId() {
		return this.institucionId;
	}

	public void setInstitucionId(long institucionId) {
		this.institucionId = institucionId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Set<Persona> getPersonas() {
		return this.personas;
	}

	public void setPersonas(Set<Persona> personas) {
		this.personas = personas;
	}
	
}