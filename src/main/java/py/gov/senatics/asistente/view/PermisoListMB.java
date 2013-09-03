package py.gov.senatics.asistente.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.gov.senatics.asistente.business.PermisoBC;
import py.gov.senatics.asistente.domain.Bookmark;
import py.gov.senatics.asistente.domain.Permiso;

@ViewController
@NextView("./permiso_edit.jsf")
@PreviousView("./permiso_list.jsf")
public class PermisoListMB extends AbstractListPageBean<Permiso, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PermisoBC permisoBC;

	private LazyDataModel<Permiso> model;
	private int pageSize = 5; // default page size

	@Override
	protected List<Permiso> handleResultList() {
		return this.permisoBC.findAll();
	}

	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter
				.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				permisoBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

	public LazyDataModel<Permiso> getModel() {
		return model;
	}

	public int getPageSize() {
		return pageSize;
	}
		
}