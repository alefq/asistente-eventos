package py.gov.senatics.asistente.view;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.ticpy.tekoporu.annotation.NextView;
import org.ticpy.tekoporu.annotation.PreviousView;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;
import org.ticpy.tekoporu.transaction.Transactional;
import py.gov.senatics.asistente.business.BookmarkBC;
import py.gov.senatics.asistente.domain.Bookmark;

@ViewController
@NextView("./bookmark_edit.jsf")
@PreviousView("./bookmark_list.jsf")
public class BookmarkListMB extends AbstractListPageBean<Bookmark, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private BookmarkBC bookmarkBC;

	@Override
	protected List<Bookmark> handleResultList() {

		return this.bookmarkBC.findAll();
	}

	@Transactional
	public String deleteSelection() {

		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter
				.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				bookmarkBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}
