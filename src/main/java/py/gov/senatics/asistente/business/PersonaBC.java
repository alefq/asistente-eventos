package py.gov.senatics.asistente.business;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;

import py.gov.senatics.asistente.domain.Persona;
import py.gov.senatics.asistente.persistence.PersonaDAO;

@BusinessController
public class PersonaBC extends DelegateCrud<Persona, Long, PersonaDAO> {
	
	private static final long serialVersionUID = 1L;
	
}
