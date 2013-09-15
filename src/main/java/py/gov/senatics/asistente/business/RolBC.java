package py.gov.senatics.asistente.business;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import py.gov.senatics.asistente.domain.Rol;
import py.gov.senatics.asistente.persistence.RolDAO;

@BusinessController
public class RolBC extends DelegateCrud<Rol, Long, RolDAO> {

	private static final long serialVersionUID = 1L;

}
