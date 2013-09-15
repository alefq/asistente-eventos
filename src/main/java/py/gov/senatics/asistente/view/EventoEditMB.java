package py.gov.senatics.asistente.view;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;
import py.gov.senatics.asistente.business.CiudadBC;
import py.gov.senatics.asistente.business.EventoBC;
import py.gov.senatics.asistente.business.PaisBC;
import py.gov.senatics.asistente.configuration.AppConfig;
import py.gov.senatics.asistente.domain.Ciudad;
import py.gov.senatics.asistente.domain.Evento;
import py.gov.senatics.asistente.domain.Pais;
import py.gov.senatics.asistente.util.DateUtil;

@ViewController
@PreviousView("./evento_list.jsf")
public class EventoEditMB extends AbstractEditPageBean<Evento, Long> {

	private static final long serialVersionUID = 1L;

	private static final String LOGO_BANNER = "banner";
	private static final String LOGO_HEADER = "header";
	private static final String LOGO_FOOTER = "footer";

	@Inject
	private EventoBC eventoBC;
	@Inject
	private FacesContext facesContext;

	/** Variables para Seleccion de Ciudad y Pais **/

	@Inject
	private PaisBC paisBC;
	@Inject
	private CiudadBC ciudadBC;
	private Pais pais;
	private List<Pais> paises;
	private List<Ciudad> ciudades;

	/** fin varibles selccion de ciudad y pais **/

	/** Variables de fechas **/

	private java.util.Date fechaInicio;
	private java.util.Date fechaFin;

	/** Fin variables de fechas **/

	/** Variables Logo **/
	private UploadedFile logoBanner;
	private UploadedFile logoHeader;
	private UploadedFile logoFooter;

	@Inject
	private AppConfig appConfig;
	private String logoMaxFileSize;

	@PostConstruct
	public void init() {

		setPaises(paisBC.findAll());
		logoMaxFileSize = appConfig.getLogoMaxFileSize();

		if (isUpdateMode()) {
			setCiudades(ciudadBC.filterCityByCountry(getBean().getCiudad()
					.getPais().getPaisId()));
			setPais(getBean().getCiudad().getPais());
			try {
				setFechaInicio(DateUtil.sqlDateToutilDate(new Date(getBean()
						.getFechaInicio().getTime())));
				setFechaFin(DateUtil.sqlDateToutilDate(new Date(getBean()
						.getFechaFin().getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	@Transactional
	public String delete() {

		this.eventoBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {

		Evento evento = getBean();
		// convertir fechas
		try {
			evento.setFechaInicio(new Timestamp(DateUtil.utilDateToSqlDate(
					getFechaInicio()).getTime()));
			evento.setFechaFin(new Timestamp(DateUtil.utilDateToSqlDate(
					getFechaFin()).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// persistir logos en disco y rutas en tabla eventos
		try {
			if (logoBanner != null) {
				File Banner = new File(appConfig.getUploadBasePath()
						+ logoBanner.getFileName());
				FileUtils.copyInputStreamToFile(logoBanner.getInputstream(),
						Banner);
				evento.setRutaLogoBanner(appConfig.getUploadBasePath()
						+ logoBanner.getFileName());
			}

			if (logoHeader != null) {
				File Header = new File(appConfig.getUploadBasePath()
						+ logoHeader.getFileName());
				FileUtils.copyInputStreamToFile(logoHeader.getInputstream(),
						Header);
				evento.setRutaLogoCabecera(appConfig.getUploadBasePath()
						+ logoHeader.getFileName());
			}

			if (logoFooter != null) {
				File Footer = new File(appConfig.getUploadBasePath()
						+ logoFooter.getFileName());
				FileUtils.copyInputStreamToFile(logoFooter.getInputstream(),
						Footer);
				evento.setRutaLogoPie(appConfig.getUploadBasePath()
						+ logoFooter.getFileName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.eventoBC.insert(evento);
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {

		Evento evento = getBean();
		// convertir fechas
		try {
			evento.setFechaInicio(new Timestamp(DateUtil.utilDateToSqlDate(
					getFechaInicio()).getTime()));
			evento.setFechaFin(new Timestamp(DateUtil.utilDateToSqlDate(
					getFechaFin()).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// persistir logos en disco y rutas en tabla eventos
		try {
			if (logoBanner != null) {
				File Banner = new File(appConfig.getUploadBasePath()
						+ logoBanner.getFileName());
				FileUtils.copyInputStreamToFile(logoBanner.getInputstream(),
						Banner);
				evento.setRutaLogoBanner(appConfig.getUploadBasePath()
						+ logoBanner.getFileName());
			}

			if (logoHeader != null) {
				File Header = new File(appConfig.getUploadBasePath()
						+ logoHeader.getFileName());
				FileUtils.copyInputStreamToFile(logoHeader.getInputstream(),
						Header);
				evento.setRutaLogoCabecera(appConfig.getUploadBasePath()
						+ logoHeader.getFileName());
			}

			if (logoFooter != null) {
				File Footer = new File(appConfig.getUploadBasePath()
						+ logoFooter.getFileName());
				FileUtils.copyInputStreamToFile(logoFooter.getInputstream(),
						Footer);
				evento.setRutaLogoPie(appConfig.getUploadBasePath()
						+ logoFooter.getFileName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.eventoBC.update(getBean());
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {

		setBean(this.eventoBC.load(getId()));
	}

	/** Otros métodos **/

	public void updateCities() {

		if (pais != null) {
			setCiudades(ciudadBC.filterCityByCountry(pais.getPaisId()));
		} else {
			setCiudades(null);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {

		String type = (String) event.getComponent().getAttributes().get("type");

		if (type.equals(LOGO_BANNER)) {
			logoBanner = event.getFile();
		}
		if (type.equals(LOGO_HEADER)) {
			logoHeader = event.getFile();
		}
		if (type.equals(LOGO_FOOTER)) {
			logoFooter = event.getFile();
		}

		facesContext
				.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Información", "Logo " + event.getFile().getFileName()
								+ " subido con éxito."));
	}

	/** GETTERS and SETTERS **/

	public Pais getPais() {

		return pais;
	}

	public void setPais(Pais pais) {

		this.pais = pais;
	}

	public List<Pais> getPaises() {

		return paises;
	}

	public void setPaises(List<Pais> paises) {

		this.paises = paises;
	}

	public List<Ciudad> getCiudades() {

		return ciudades;
	}

	public void setCiudades(List<Ciudad> ciudades) {

		this.ciudades = ciudades;
	}

	public java.util.Date getFechaInicio() {

		return fechaInicio;
	}

	public void setFechaInicio(java.util.Date fechaInicio) {

		this.fechaInicio = fechaInicio;
	}

	public java.util.Date getFechaFin() {

		return fechaFin;
	}

	public void setFechaFin(java.util.Date fechaFin) {

		this.fechaFin = fechaFin;
	}

	public String getLogoMaxFileSize() {

		return logoMaxFileSize;
	}

	public void setLogoMaxFileSize(String logoMaxFileSize) {

		this.logoMaxFileSize = logoMaxFileSize;
	}

}
