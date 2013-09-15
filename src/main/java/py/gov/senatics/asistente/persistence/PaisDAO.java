package py.gov.senatics.asistente.persistence;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;
import py.gov.senatics.asistente.domain.Pais;

@PersistenceController
public class PaisDAO extends JPACrud<Pais, Long> {

	private static final long serialVersionUID = 1L;

}
