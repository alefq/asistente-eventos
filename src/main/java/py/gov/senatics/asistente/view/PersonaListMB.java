package py.gov.senatics.asistente.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.gov.senatics.asistente.business.PersonaBC;
import py.gov.senatics.asistente.domain.Persona;

@ViewController
@NextView("./persona_edit.jsf")
@PreviousView("./persona_list.jsf")
public class PersonaListMB extends AbstractListPageBean<Persona, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PersonaBC personaBC;
	
	@Override
	protected List<Persona> handleResultList() {
		return this.personaBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				personaBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}