package py.gov.senatics.asistente.business;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;

import py.gov.senatics.asistente.domain.Pais;
import py.gov.senatics.asistente.persistence.PaisDAO;

@BusinessController
public class PaisBC extends DelegateCrud<Pais, Long, PaisDAO> {
	
	private static final long serialVersionUID = 1L;
	
}
