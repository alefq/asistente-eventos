package py.gov.senatics.asistente.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import py.gov.senatics.asistente.business.CiudadBC;
import py.gov.senatics.asistente.domain.Ciudad;

@Named("ciudadConverter")
public class CiudadConverter implements Converter {

	@Inject
	CiudadBC ciudadBC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		if (value == null || value.equals("") || value.length() == 0) {
			return null;
		}
		Long id = Long.parseLong(value);
		return ciudadBC.load(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value == null) {
			return "";
		}
		Ciudad ciudad = (Ciudad) value;
		return String.valueOf(ciudad.getCiudadId());
	}

}
