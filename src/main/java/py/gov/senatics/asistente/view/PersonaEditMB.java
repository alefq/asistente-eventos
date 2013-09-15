package py.gov.senatics.asistente.view;

import javax.inject.Inject;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;
import py.gov.senatics.asistente.business.PersonaBC;
import py.gov.senatics.asistente.domain.Persona;

@ViewController
@PreviousView("./persona_list.jsf")
public class PersonaEditMB extends AbstractEditPageBean<Persona, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PersonaBC personaBC;

	@Override
	@Transactional
	public String delete() {

		this.personaBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {

		this.personaBC.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {

		this.personaBC.update(getBean());
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {

		setBean(this.personaBC.load(getId()));
	}

}
