package py.gov.senatics.asistente.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the PERSONA database table.
 * 
 */
@Entity
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PERSONA_PERSONAID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSONA_PERSONAID_GENERATOR")
	@Column(name = "PERSONA_ID")
	private long personaId;

	private String apellido;

	private String cargo;

	@Column(name = "DOCUMENTO_NUMERO")
	private String documentoNumero;

	@Column(name = "DOCUMENTO_TIPO")
	private String documentoTipo;

	@Column(name = "EMAIL_INSTITUCIONAL")
	private String emailInstitucional;

	@Column(name = "EMAIL_PERSONAL")
	private String emailPersonal;

	private short estado;

	@Column(name = "INSTITUCION_NO_REGISTRADA")
	private String institucionNoRegistrada;

	private String nombre;

	@Column(name = "TELEFONO_CELULAR")
	private String telefonoCelular;

	@Column(name = "TELEFONO_INSTITUCIONAL")
	private String telefonoInstitucional;

	@Column(name = "TOKEN_CONFIRMACION")
	private String tokenConfirmacion;

	@Column(name = "TOKEN_TIMESTAMP")
	private Timestamp tokenTimestamp;

	// bi-directional many-to-one association to Institucion
	@ManyToOne
	@JoinColumn(name = "INSTITUCION_ID")
	private Institucion institucion;

	public Persona() {

	}

	public long getPersonaId() {

		return this.personaId;
	}

	public void setPersonaId(long personaId) {

		this.personaId = personaId;
	}

	public String getApellido() {

		return this.apellido;
	}

	public void setApellido(String apellido) {

		this.apellido = apellido;
	}

	public String getCargo() {

		return this.cargo;
	}

	public void setCargo(String cargo) {

		this.cargo = cargo;
	}

	public String getDocumentoNumero() {

		return this.documentoNumero;
	}

	public void setDocumentoNumero(String documentoNumero) {

		this.documentoNumero = documentoNumero;
	}

	public String getDocumentoTipo() {

		return this.documentoTipo;
	}

	public void setDocumentoTipo(String documentoTipo) {

		this.documentoTipo = documentoTipo;
	}

	public String getEmailInstitucional() {

		return this.emailInstitucional;
	}

	public void setEmailInstitucional(String emailInstitucional) {

		this.emailInstitucional = emailInstitucional;
	}

	public String getEmailPersonal() {

		return this.emailPersonal;
	}

	public void setEmailPersonal(String emailPersonal) {

		this.emailPersonal = emailPersonal;
	}

	public short getEstado() {

		return this.estado;
	}

	public void setEstado(short estado) {

		this.estado = estado;
	}

	public String getInstitucionNoRegistrada() {

		return this.institucionNoRegistrada;
	}

	public void setInstitucionNoRegistrada(String institucionNoRegistrada) {

		this.institucionNoRegistrada = institucionNoRegistrada;
	}

	public String getNombre() {

		return this.nombre;
	}

	public void setNombre(String nombre) {

		this.nombre = nombre;
	}

	public String getTelefonoCelular() {

		return this.telefonoCelular;
	}

	public void setTelefonoCelular(String telefonoCelular) {

		this.telefonoCelular = telefonoCelular;
	}

	public String getTelefonoInstitucional() {

		return this.telefonoInstitucional;
	}

	public void setTelefonoInstitucional(String telefonoInstitucional) {

		this.telefonoInstitucional = telefonoInstitucional;
	}

	public String getTokenConfirmacion() {

		return this.tokenConfirmacion;
	}

	public void setTokenConfirmacion(String tokenConfirmacion) {

		this.tokenConfirmacion = tokenConfirmacion;
	}

	public Timestamp getTokenTimestamp() {

		return this.tokenTimestamp;
	}

	public void setTokenTimestamp(Timestamp tokenTimestamp) {

		this.tokenTimestamp = tokenTimestamp;
	}

	public Institucion getInstitucion() {

		return this.institucion;
	}

	public void setInstitucion(Institucion institucion) {

		this.institucion = institucion;
	}

}
