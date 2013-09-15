package py.gov.senatics.asistente.business;

import javax.inject.Inject;
import org.ticpy.tekoporu.annotation.Startup;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.gov.senatics.asistente.domain.Pais;
import py.gov.senatics.asistente.persistence.PaisDAO;

@BusinessController
public class PaisBC extends DelegateCrud<Pais, Long, PaisDAO> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PaisDAO paisDAO;

	@Startup
	@Transactional
	public void load() {

		if (findAll().isEmpty()) {
			insert(new Pais("Paraguay"));
			insert(new Pais("Argentina"));
			System.out.println(paisDAO.findAll().get(0).getNombre() + " ");
		}

	}

}
