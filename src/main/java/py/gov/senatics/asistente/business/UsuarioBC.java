package py.gov.senatics.asistente.business;

import javax.inject.Inject;
import org.ticpy.tekoporu.annotation.Startup;
import org.ticpy.tekoporu.security.util.Sha256SaltedGenerator;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.template.DelegateCrud;
import org.ticpy.tekoporu.transaction.Transactional;
import py.gov.senatics.asistente.domain.Usuario;
import py.gov.senatics.asistente.persistence.UsuarioDAO;

@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Startup
	@Transactional
	public void load() {

		if (findAll().isEmpty()) {
			insert(new Usuario("123456", "user", "random_salt_value_user"));
			insert(new Usuario("admin1", "admin", "random_salt_value_admin"));
			System.out.println(usuarioDAO.findAll().get(0).getNombre() + " ");
		}

	}

	@Override
	public void insert(Usuario usuario) {

		String salt = "random_salt_value_";
		usuario.setContrasenha(Sha256SaltedGenerator.simpleSaltedHash(
				usuario.getNombre(), usuario.getContrasenha(), salt));
		/* El salt en el generador le concatena el nombre de usuario */
		usuario.setSalt(salt + usuario.getNombre());
		super.insert(usuario);
	}

	@Override
	public void update(Usuario usuario) {

		/* Se asignó una nueva contraseña */
		if (usuario.getContrasenha() != null
				&& usuario.getContrasenha().trim().length() > 0) {
			String salt = "random_salt_value_";
			usuario.setContrasenha(Sha256SaltedGenerator.simpleSaltedHash(
					usuario.getNombre(), usuario.getContrasenha(), salt));
			/* El salt en el generador le concatena el nombre de usuario */
			usuario.setSalt(salt + usuario.getNombre());
		}
		super.update(usuario);
	}

}
