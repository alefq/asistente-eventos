package py.gov.senatics.asistente.view;

import javax.inject.Inject;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractEditPageBean;
import org.ticpy.tekoporu.transaction.Transactional;
import py.gov.senatics.asistente.business.PaisBC;
import py.gov.senatics.asistente.domain.Pais;

@ViewController
@PreviousView("./pais_list.jsf")
public class PaisEditMB extends AbstractEditPageBean<Pais, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PaisBC paisBC;

	@Override
	@Transactional
	public String delete() {

		this.paisBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {

		this.paisBC.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {

		this.paisBC.update(getBean());
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {

		setBean(this.paisBC.load(getId()));
	}

}
