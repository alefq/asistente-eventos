package py.gov.senatics.asistente.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the USUARIO database table.
 * 
 */
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "USUARIO_USUARIOID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_USUARIOID_GENERATOR")
	@Column(name = "USUARIO_ID")
	private long usuarioId;

	@Column(name = "CLAVE_PRIVADA")
	private String clavePrivada;

	@Column(name = "CLAVE_PUBLICA")
	private String clavePublica;

	private String contrasenha;

	private String nombre;

	private String salt;

	// bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name = "PERSONA_ID")
	private Persona persona;

	public Usuario() {

	}

	public Usuario(String contrasenha, String nombre, String salt) {

		super();
		this.contrasenha = contrasenha;
		this.nombre = nombre;
		this.salt = salt;
	}

	public long getUsuarioId() {

		return this.usuarioId;
	}

	public void setUsuarioId(long usuarioId) {

		this.usuarioId = usuarioId;
	}

	public String getClavePrivada() {

		return this.clavePrivada;
	}

	public void setClavePrivada(String clavePrivada) {

		this.clavePrivada = clavePrivada;
	}

	public String getClavePublica() {

		return this.clavePublica;
	}

	public void setClavePublica(String clavePublica) {

		this.clavePublica = clavePublica;
	}

	public String getContrasenha() {

		return this.contrasenha;
	}

	public void setContrasenha(String contrasenha) {

		this.contrasenha = contrasenha;
	}

	public String getNombre() {

		return this.nombre;
	}

	public void setNombre(String nombre) {

		this.nombre = nombre;
	}

	public String getSalt() {

		return this.salt;
	}

	public void setSalt(String salt) {

		this.salt = salt;
	}

	public Persona getPersona() {

		return this.persona;
	}

	public void setPersona(Persona persona) {

		this.persona = persona;
	}

}
