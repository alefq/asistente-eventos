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
 * The persistent class for the EVENTO database table.
 * 
 */
@Entity
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EVENTO_EVENTOID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EVENTO_EVENTOID_GENERATOR")
	@Column(name="EVENTO_ID")
	private long eventoId;

	@Column(name="CONTACTO_EMAIL")
	private String contactoEmail;

	@Column(name="CONTACTO_TELEFONO")
	private String contactoTelefono;

	@Column(name="DIRECCION_LOCAL")
	private String direccionLocal;

	@Column(name="FECHA_FIN")
	private Timestamp fechaFin;

	@Column(name="FECHA_INICIO")
	private Timestamp fechaInicio;

	@Column(name="NIVEL_ENTIDAD_CODIGO")
	private long nivelEntidadCodigo;

	private String nombre;

	@Column(name="NOMBRE_ABREVIADO")
	private String nombreAbreviado;

	@Column(name="NOMBRE_EVENTO")
	private String nombreEvento;

	@Column(name="NOMBRE_LOCAL")
	private String nombreLocal;

	private String observacion;

	private String observaciones;

	@Column(name="RUTA_LOGO_BANNER")
	private String rutaLogoBanner;

	@Column(name="RUTA_LOGO_CABECERA")
	private String rutaLogoCabecera;

	@Column(name="RUTA_LOGO_PIE")
	private String rutaLogoPie;

	@Column(name="URL_GEOLOCALIZACION")
	private String urlGeolocalizacion;

	//bi-directional many-to-one association to Ciudad
    @ManyToOne
	@JoinColumn(name="CIUDAD_ID")
	private Ciudad ciudad;

    public Evento() {
    }

	public long getEventoId() {
		return this.eventoId;
	}

	public void setEventoId(long eventoId) {
		this.eventoId = eventoId;
	}

	public String getContactoEmail() {
		return this.contactoEmail;
	}

	public void setContactoEmail(String contactoEmail) {
		this.contactoEmail = contactoEmail;
	}

	public String getContactoTelefono() {
		return this.contactoTelefono;
	}

	public void setContactoTelefono(String contactoTelefono) {
		this.contactoTelefono = contactoTelefono;
	}

	public String getDireccionLocal() {
		return this.direccionLocal;
	}

	public void setDireccionLocal(String direccionLocal) {
		this.direccionLocal = direccionLocal;
	}

	public Timestamp getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public long getNivelEntidadCodigo() {
		return this.nivelEntidadCodigo;
	}

	public void setNivelEntidadCodigo(long nivelEntidadCodigo) {
		this.nivelEntidadCodigo = nivelEntidadCodigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreAbreviado() {
		return this.nombreAbreviado;
	}

	public void setNombreAbreviado(String nombreAbreviado) {
		this.nombreAbreviado = nombreAbreviado;
	}

	public String getNombreEvento() {
		return this.nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public String getNombreLocal() {
		return this.nombreLocal;
	}

	public void setNombreLocal(String nombreLocal) {
		this.nombreLocal = nombreLocal;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRutaLogoBanner() {
		return this.rutaLogoBanner;
	}

	public void setRutaLogoBanner(String rutaLogoBanner) {
		this.rutaLogoBanner = rutaLogoBanner;
	}

	public String getRutaLogoCabecera() {
		return this.rutaLogoCabecera;
	}

	public void setRutaLogoCabecera(String rutaLogoCabecera) {
		this.rutaLogoCabecera = rutaLogoCabecera;
	}

	public String getRutaLogoPie() {
		return this.rutaLogoPie;
	}

	public void setRutaLogoPie(String rutaLogoPie) {
		this.rutaLogoPie = rutaLogoPie;
	}

	public String getUrlGeolocalizacion() {
		return this.urlGeolocalizacion;
	}

	public void setUrlGeolocalizacion(String urlGeolocalizacion) {
		this.urlGeolocalizacion = urlGeolocalizacion;
	}

	public Ciudad getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	
}