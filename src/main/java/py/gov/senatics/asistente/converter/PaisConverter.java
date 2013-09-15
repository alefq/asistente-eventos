package py.gov.senatics.asistente.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import py.gov.senatics.asistente.business.PaisBC;
import py.gov.senatics.asistente.domain.Pais;

@Named("paisConverter")
public class PaisConverter implements Converter {

	@Inject
	PaisBC paisBC;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		if (value == null || value.equals("") || value.length() == 0) {
			return null;
		}
		Long id = Long.parseLong(value);
		return paisBC.load(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value == null) {
			return "";
		}
		Pais pais = (Pais) value;
		return String.valueOf(pais.getPaisId());
	}

}
