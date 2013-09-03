/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package py.gov.setics.asistente.event;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.slf4j.Logger;

/**
 * @version $Revision$ $Date$
 */
@Singleton
@Lock(LockType.READ)
public class Scheduler {

	@Inject
	private Logger logger;

	@Resource
	private TimerService timerService;

	@Inject
	private Event<TestEvent> event;

	@Inject
	private BeanManager beanManager;

	public void scheduleEvent(ScheduleExpression schedule, Object event,
			Annotation... qualifiers) {

		timerService.createCalendarTimer(schedule, new TimerConfig(
				new EventConfig(event, qualifiers), false));
	}

	@Timeout
	private void timeout(Timer timer) {
		final EventConfig config = (EventConfig) timer.getInfo();
		logger.info("Timeout: " + timer);
		event.fire((TestEvent) config.getEvent());
		// beanManager.fireEvent(config.getEvent(), config.getQualifiers());
	}

	// Doesn't actually need to be serializable, just has to implement it
	private final class EventConfig implements Serializable {

		private final Object event;
		private final Annotation[] qualifiers;

		private EventConfig(Object event, Annotation[] qualifiers) {
			this.event = event;
			this.qualifiers = qualifiers;
		}

		public Object getEvent() {
			return event;
		}

		public Annotation[] getQualifiers() {
			return qualifiers;
		}
	}
}
