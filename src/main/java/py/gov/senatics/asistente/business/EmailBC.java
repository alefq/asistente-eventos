package py.gov.senatics.asistente.business;

import java.io.Serializable;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.ticpy.tekoporu.annotation.Name;
import org.ticpy.tekoporu.stereotype.BusinessController;
import py.gov.senatics.asistente.configuration.MailConfig;

@BusinessController
@Name("emailBC")
public class EmailBC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6502390983460518583L;

	@Inject
	private MailConfig mailConfig;

	@Inject
	private Logger logger;

	public void enviarAviso() {

		logger.info("Mail Server: " + mailConfig.getServer());
		logger.info("Mail Server port: " + mailConfig.getPort());
		logger.info("Mail Server user: " + mailConfig.getUser());
		logger.info("Mail Server password: " + mailConfig.getPassword());
	}

}
