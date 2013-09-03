package py.gov.senatics.asistente.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.gov.senatics.asistente.business.CiudadBC;
import py.gov.senatics.asistente.domain.Ciudad;

@ViewController
@NextView("./ciudad_edit.jsf")
@PreviousView("./ciudad_list.jsf")
public class CiudadListMB extends AbstractListPageBean<Ciudad, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private CiudadBC ciudadBC;
	
	@Override
	protected List<Ciudad> handleResultList() {
		return this.ciudadBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				ciudadBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}