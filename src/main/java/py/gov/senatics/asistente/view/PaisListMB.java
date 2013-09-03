package py.gov.senatics.asistente.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.gov.senatics.asistente.business.PaisBC;
import py.gov.senatics.asistente.domain.Pais;

@ViewController
@NextView("./pais_edit.jsf")
@PreviousView("./pais_list.jsf")
public class PaisListMB extends AbstractListPageBean<Pais, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PaisBC paisBC;
	
	@Override
	protected List<Pais> handleResultList() {
		return this.paisBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				paisBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}