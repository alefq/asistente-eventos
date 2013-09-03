package py.gov.senatics.asistente.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


/**
 * The persistent class for the CIUDAD database table.
 * 
 */
@Entity
public class Ciudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CIUDAD_CIUDADID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CIUDAD_CIUDADID_GENERATOR")
	@Column(name="CIUDAD_ID")
	private long ciudadId;

	private String nombre;

	//bi-directional many-to-one association to Pais
    @ManyToOne
	@JoinColumn(name="PAIS_ID")
	private Pais pais;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="ciudad")
	private Set<Evento> eventos;

    public Ciudad() {
    }

	public long getCiudadId() {
		return this.ciudadId;
	}

	public void setCiudadId(long ciudadId) {
		this.ciudadId = ciudadId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Pais getPais() {
		return this.pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	public Set<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}
	
}