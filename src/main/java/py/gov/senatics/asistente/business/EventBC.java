package py.gov.senatics.asistente.business;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.ejb.AccessTimeout;
import javax.ejb.ScheduleExpression;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.ticpy.tekoporu.annotation.Name;
import org.ticpy.tekoporu.stereotype.BusinessController;
import py.gov.senatics.asistente.event.Scheduler;
import py.gov.senatics.asistente.event.TestEvent;

@BusinessController
@Name(value = "eventMB")
public class EventBC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2949645766787859209L;

	@Inject
	private Scheduler scheduler;

	@Inject
	private Logger logger;

	public String setTimer() {

		final ScheduleExpression schedule = new ScheduleExpression().hour("*")
				.minute("*").second("*/5");

		scheduler.scheduleEvent(schedule, new TestEvent("five"));
		return null;
	}

	@AccessTimeout(value = 1, unit = TimeUnit.MINUTES)
	public void observe(@Observes TestEvent event) {

		if ("five".equals(event.getMessage())) {
			logger.info("¡Evento recibido!");
		}
	}

}
