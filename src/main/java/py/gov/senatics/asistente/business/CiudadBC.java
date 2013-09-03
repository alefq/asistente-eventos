package py.gov.senatics.asistente.business;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;

import py.gov.senatics.asistente.domain.Ciudad;
import py.gov.senatics.asistente.persistence.CiudadDAO;

@BusinessController
public class CiudadBC extends DelegateCrud<Ciudad, Long, CiudadDAO> {
	
	private static final long serialVersionUID = 1L;
	
}
