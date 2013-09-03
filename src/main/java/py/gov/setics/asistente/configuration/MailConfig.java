package py.gov.setics.asistente.configuration;

import org.ticpy.tekoporu.configuration.Configuration;

@Configuration(resource = "mail")
public class MailConfig {

	private String server;
	private Integer port;
	private String user;
	private String password;

	public String getServer() {
		return server;
	}

	public Integer getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

}