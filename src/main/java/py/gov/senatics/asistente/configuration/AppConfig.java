package py.gov.senatics.asistente.configuration;

import org.ticpy.tekoporu.configuration.Configuration;

@Configuration(resource = "app")
public class AppConfig {

	private String logoMaxFileSize;
	private String uploadBasePath;

	public String getLogoMaxFileSize() {

		return logoMaxFileSize;
	}

	public String getUploadBasePath() {

		return uploadBasePath;
	}

}
