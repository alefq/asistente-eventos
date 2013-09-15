package py.gov.senatics.asistente.view;

import javax.inject.Inject;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;
import py.gov.senatics.asistente.business.InstitucionBC;
import py.gov.senatics.asistente.domain.Institucion;

@ViewController
@PreviousView("./institucion_list.jsf")
public class InstitucionEditMB extends AbstractEditPageBean<Institucion, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private InstitucionBC institucionBC;

	@Override
	@Transactional
	public String delete() {

		this.institucionBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {

		this.institucionBC.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {

		this.institucionBC.update(getBean());
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {

		setBean(this.institucionBC.load(getId()));
	}

}
