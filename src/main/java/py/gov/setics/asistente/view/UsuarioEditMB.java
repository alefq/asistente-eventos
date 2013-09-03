package py.gov.setics.asistente.view;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.gov.senatics.asistente.domain.Usuario;
import py.gov.setics.asistente.business.UsuarioBC;

@ViewController
@PreviousView("./usuario_list.jsf")
public class UsuarioEditMB extends AbstractEditPageBean<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioBC usuarioBC;

	@Override
	@Transactional
	public String delete() {
		this.usuarioBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.usuarioBC.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {

		this.usuarioBC.update(getBean());
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		Usuario usuario = this.usuarioBC.load(getId());
		usuario.setContrasenha(null);
		setBean(usuario);
	}

}