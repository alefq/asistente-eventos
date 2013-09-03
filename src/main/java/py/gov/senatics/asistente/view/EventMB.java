package py.gov.senatics.asistente.view;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.ticpy.tekoporu.annotation.Name;
import org.ticpy.tekoporu.stereotype.ViewController;
import org.ticpy.tekoporu.template.AbstractListPageBean;

import py.gov.senatics.asistente.business.EmailBC;
import py.gov.senatics.asistente.business.EventBC;
import py.gov.senatics.asistente.domain.Bookmark;

@ViewController
@Name(value = "eventMB")
public class EventMB extends AbstractListPageBean<Bookmark, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5835191527760414088L;

	@Inject
	private EventBC eventBC;
	
	@Inject
	private EmailBC emailBC;

	@Override
	protected List<Bookmark> handleResultList() {
		// TODO Auto-generated method stub
		return null;
	}

	public String setTimer() {
		eventBC.setTimer();
		emailBC.enviarAviso();
		return null;
	}

}
