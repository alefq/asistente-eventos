package py.gov.senatics.asistente.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.weld.Container;
import org.jboss.weld.context.bound.BoundRequestContext;
import org.slf4j.Logger;

@Stateless
public class EventObserver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5333747827481836108L;
	@Inject
	private Logger logger;

	private BoundRequestContext context;
	private Map<String, Object> request;

	@AccessTimeout(value = 1, unit = TimeUnit.MINUTES)
	public void observe(@Observes TestEvent event) {
		initContext();
		if ("five".equals(event.getMessage())) {
			logger.info("¡Evento recibido!");
		}
		closeContext();
	}

	private void initContext() {

		BoundRequestContext context = Container.instance().deploymentManager()
				.instance().select(BoundRequestContext.class).get();
		Map<String, Object> request = new HashMap<String, Object>();
		context.associate(request);
		context.activate();
	}

	private void closeContext() {

		if (context != null && context.isActive()) {
			context.deactivate();
			context.dissociate(request);
		}
	}
}
