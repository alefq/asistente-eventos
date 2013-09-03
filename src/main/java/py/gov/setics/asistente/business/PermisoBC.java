package py.gov.setics.asistente.business;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;

import py.gov.senatics.asistente.domain.Permiso;
import py.gov.setics.asistente.persistence.PermisoDAO;

@BusinessController
public class PermisoBC extends DelegateCrud<Permiso, Long, PermisoDAO> {
	
	private static final long serialVersionUID = 1L;
	
}
