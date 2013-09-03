package py.gov.setics.asistente.view;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.gov.senatics.asistente.domain.Permiso;
import py.gov.setics.asistente.business.PermisoBC;

@ViewController
@PreviousView("./permiso_list.jsf")
public class PermisoEditMB extends AbstractEditPageBean<Permiso, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PermisoBC permisoBC;
	
	@Override
	@Transactional
	public String delete() {
		this.permisoBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.permisoBC.insert(getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.permisoBC.update(getBean());
		return getPreviousView();
	}
	
	@Override
	protected void handleLoad() {
		setBean(this.permisoBC.load(getId()));
	}

}