package py.gov.senatics.asistente.view;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.gov.senatics.asistente.business.EventoBC;
import py.gov.senatics.asistente.domain.Evento;

@ViewController
@PreviousView("./evento_list.jsf")
public class EventoEditMB extends AbstractEditPageBean<Evento, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EventoBC eventoBC;
	
	@Override
	@Transactional
	public String delete() {
		this.eventoBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.eventoBC.insert(getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.eventoBC.update(getBean());
		return getPreviousView();
	}
	
	@Override
	protected void handleLoad() {
		setBean(this.eventoBC.load(getId()));
	}

}