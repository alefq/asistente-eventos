package py.gov.senatics.asistente.view;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.gov.senatics.asistente.business.RolBC;
import py.gov.senatics.asistente.domain.Rol;

@ViewController
@PreviousView("./rol_list.jsf")
public class RolEditMB extends AbstractEditPageBean<Rol, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private RolBC rolBC;
	
	@Override
	@Transactional
	public String delete() {
		this.rolBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.rolBC.insert(getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.rolBC.update(getBean());
		return getPreviousView();
	}
	
	@Override
	protected void handleLoad() {
		setBean(this.rolBC.load(getId()));
	}

}