package py.gov.setics.asistente.view;

import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.transaction.Transactional;

import py.gov.senatics.asistente.domain.Usuario;
import py.gov.setics.asistente.business.UsuarioBC;

@SessionScoped
@ViewController
@NextView("/admin/usuario_edit.xhtml")
@PreviousView("/admin/usuario_list.xhtml")
public class UsuarioListMB extends AbstractListPageBean<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioBC usuarioBC;

	private LazyDataModel<Usuario> model;

	public UsuarioBC getUsuarioBC() {
		return usuarioBC;
	}

	public LazyDataModel<Usuario> getModel() {
		return model;
	}

	public int getPageSize() {
		return pageSize;
	}

	private int pageSize = 5; // default page size

	@Override
	protected List<Usuario> handleResultList() {
		return this.usuarioBC.findAll();
	}

	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter
				.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				usuarioBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}