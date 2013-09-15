package py.gov.senatics.asistente.action;

import java.io.Serializable;
import javax.inject.Inject;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.ticpy.tekoporu.annotation.Name;
import org.ticpy.tekoporu.annotation.Startup;
import org.ticpy.tekoporu.scheduler.ISchedulerAction;
import org.ticpy.tekoporu.scheduler.Scheduler;
import org.ticpy.tekoporu.scheduler.SchedulerActionManager;
import org.ticpy.tekoporu.stereotype.BusinessController;
import org.ticpy.tekoporu.util.Beans;

@Scheduler(expression = "14:42:00 EVERY_MINUTE")
@Name("demoScheduler")
@BusinessController
public class DemoScheduler implements ISchedulerAction, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7185856082262566555L;
	@Inject
	Logger log;

	@Startup
	public void initialize() {

		SchedulerActionManager scheduler;
		try {
			scheduler = new SchedulerActionManager();
			scheduler.execute();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void execute() {

		System.out.println(hashCode());
		if (log == null)
			log = Beans.getReference(Logger.class);
		log.debug("Ejecutando tarea programada...");
	}
}
