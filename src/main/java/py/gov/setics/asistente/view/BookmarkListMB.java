/*
 * TICPY Framework
 * Copyright (C) 2012 Plan Director TICs
 *
----------------------------------------------------------------------------
 * Originally developed by SERPRO
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 *
----------------------------------------------------------------------------
 * This file is part of TICPY Framework.
 *
 * TICPY Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 *
----------------------------------------------------------------------------
 * Este archivo es parte del Framework TICPY.
 *
 * El TICPY Framework es software libre; Usted puede redistribuirlo y/o
 * modificarlo bajo los términos de la GNU Lesser General Public Licence versión 3
 * de la Free Software Foundation.
 *
 * Este programa es distribuido con la esperanza que sea de utilidad,
 * pero sin NINGUNA GARANTÍA; sin una garantía implícita de ADECUACION a cualquier
 * MERCADO o APLICACION EN PARTICULAR. vea la GNU General Public Licence
 * más detalles.
 *
 * Usted deberá haber recibido una copia de la GNU Lesser General Public Licence versión 3
 * "LICENCA.txt", junto con este programa; en caso que no, acceda a <http://www.gnu.org/licenses/>
 * o escriba a la Free Software Foundation (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package py.gov.setics.asistente.view;

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

import py.gov.setics.asistente.business.BookmarkBC;
import py.gov.setics.asistente.domain.Bookmark;

@ViewController
@NextView("/bookmark_edit.xhtml")
@PreviousView("/bookmark_list.xhtml")
public class BookmarkListMB extends AbstractListPageBean<Bookmark, Long> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BookmarkBC bc;
	
	private LazyDataModel<Bookmark> model;	
	private int pageSize = 5; //default page size
		
	@SuppressWarnings("serial")
	@PostConstruct
	public void loadLazyModel() {
		
		model = new LazyDataModel<Bookmark>() {
			@Override
			public List<Bookmark> load(int first, int pageSize, String sortField,
					boolean sortOrder, Map<String, String> filters) {
				
					if(sortField == null) sortField = "id"; //default sort field
					
					List<Bookmark> bookmark = new ArrayList<Bookmark>();
					bookmark = bc.findPage(pageSize, first, sortField, sortOrder);
					
					//sort in ram
					//if (sortField != null) {
					//	Collections.sort(bookmark,new LazySorter<Bookmark>(sortField,sortOrder));
					//}
					
					return bookmark;
			}
		};
		
		model.setRowCount(bc.count());
		model.setPageSize(pageSize);
		
	}
	
	@Override
	protected List<Bookmark> handleResultList() {
		return this.bc.findAll();		
	}

	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);

			if (delete) {
				bc.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	public LazyDataModel<Bookmark> getModel() {
		return model;
	}

	public int getPageSize() {
		return pageSize;
	}

}