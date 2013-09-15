package py.gov.senatics.asistente.view;

import javax.inject.Inject;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;
import py.gov.senatics.asistente.business.CiudadBC;
import py.gov.senatics.asistente.domain.Ciudad;

@ViewController
@PreviousView("./ciudad_list.jsf")
public class CiudadEditMB extends AbstractEditPageBean<Ciudad, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private CiudadBC ciudadBC;

	@Override
	@Transactional
	public String delete() {

		this.ciudadBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {

		this.ciudadBC.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {

		this.ciudadBC.update(getBean());
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {

		setBean(this.ciudadBC.load(getId()));
	}

}
