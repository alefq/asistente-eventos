package py.gov.setics.asistente.persistence;

import org.ticpy.tekoporu.stereotype.PersistenceController;
import org.ticpy.tekoporu.template.JPACrud;

import py.gov.senatics.asistente.domain.Usuario;

@PersistenceController
public class UsuarioDAO extends JPACrud<Usuario, Long> {

	private static final long serialVersionUID = 1L;
	

}
