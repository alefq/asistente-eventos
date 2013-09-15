package py.gov.senatics.asistente.business;

import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import py.gov.senatics.asistente.domain.Evento;
import py.gov.senatics.asistente.persistence.EventoDAO;

@BusinessController
public class EventoBC extends DelegateCrud<Evento, Long, EventoDAO> {

	private static final long serialVersionUID = 1L;

}
