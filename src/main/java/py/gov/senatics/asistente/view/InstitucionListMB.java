package py.gov.senatics.asistente.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.gov.senatics.asistente.business.InstitucionBC;
import py.gov.senatics.asistente.domain.Institucion;

@ViewController
@NextView("./institucion_edit.jsf")
@PreviousView("./institucion_list.jsf")
public class InstitucionListMB extends AbstractListPageBean<Institucion, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private InstitucionBC institucionBC;
	
	@Override
	protected List<Institucion> handleResultList() {
		return this.institucionBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				institucionBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}