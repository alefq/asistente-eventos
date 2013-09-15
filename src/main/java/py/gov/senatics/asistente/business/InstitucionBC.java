package py.gov.senatics.asistente.business;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import py.gov.senatics.asistente.domain.Institucion;
import py.gov.senatics.asistente.persistence.InstitucionDAO;

@BusinessController
public class InstitucionBC extends
		DelegateCrud<Institucion, Long, InstitucionDAO> {

	private static final long serialVersionUID = 1L;

}
