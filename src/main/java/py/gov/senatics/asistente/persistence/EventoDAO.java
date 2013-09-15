package py.gov.senatics.asistente.persistence;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import py.gov.senatics.asistente.domain.Evento;

@PersistenceController
public class EventoDAO extends JPACrud<Evento, Long> {

	private static final long serialVersionUID = 1L;

}
