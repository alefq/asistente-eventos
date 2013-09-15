package py.gov.senatics.asistente.business;

import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.annotation.Startup;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.gov.senatics.asistente.domain.Ciudad;
import py.gov.senatics.asistente.persistence.CiudadDAO;
import py.gov.senatics.asistente.persistence.PaisDAO;

@BusinessController
public class CiudadBC extends DelegateCrud<Ciudad, Long, CiudadDAO> {

	private static final long serialVersionUID = 1L;

	@Inject
	private CiudadDAO ciudadDAO;

	@Inject
	private PaisDAO paisDAO;

	@Startup
	@Transactional
	public void load() {

		if (findAll().isEmpty()) {
			insert(new Ciudad("Asunción", paisDAO.load(1l)));
			insert(new Ciudad("Lambaré", paisDAO.load(1l)));
			insert(new Ciudad("Buenos Aires", paisDAO.load(2l)));
			System.out.println(ciudadDAO.findAll().get(0).getNombre() + " ");
		}
	}

	/** Consultas a Base de Datos */

	public List<Ciudad> filterCityByCountry(Long paisId) {

		return ciudadDAO.filterCityByCountry(paisId);
	}

}
