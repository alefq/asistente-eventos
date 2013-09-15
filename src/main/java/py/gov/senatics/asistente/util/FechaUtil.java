package py.gov.senatics.asistente.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import org.slf4j.Logger;

public class FechaUtil {

	@Inject
	static Logger log;

	public static Date desplazarFecha(java.util.Date pFecha, Integer segundos,
			Integer minutos, Integer horas, Integer pDias, Integer pMeses,
			Integer pAnos, Boolean atrasar) {

		return desplazarFecha(pFecha, 0, segundos, minutos, horas, pDias,
				pMeses, pAnos, atrasar);
	}

	public static Date desplazarFecha(java.util.Date pFecha,
			Integer milisegundos, Integer segundos, Integer minutos,
			Integer horas, Integer pDias, Integer pMeses, Integer pAnos,
			Boolean atrasar) {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(pFecha);
		int factorAtraso = atrasar ? -1 : 1;

		if (pDias != null) {
			c1.add(Calendar.DATE, pDias.intValue() * factorAtraso);
		}
		if (pMeses != null) {
			c1.add(Calendar.MONTH, pMeses.intValue() * factorAtraso);
		}
		if (pAnos != null) {
			c1.add(Calendar.YEAR, pAnos.intValue() * factorAtraso);
		}

		c1.add(Calendar.SECOND, segundos != null ? (segundos * factorAtraso)
				: 0);
		c1.add(Calendar.MINUTE, minutos != null ? (minutos * factorAtraso) : 0);
		c1.add(Calendar.HOUR, horas != null ? (horas * factorAtraso) : 0);
		c1.add(Calendar.MILLISECOND,
				milisegundos != null ? (milisegundos * factorAtraso) : 0);

		return new Date(c1.getTimeInMillis());
	}

	public static Date parseFecha(String fechaString) {

		Date fecha = null;
		DateFormat parseadorFecha = new SimpleDateFormat("ddMMyyyy HH:mm");

		try {
			fecha = parseadorFecha.parse(fechaString);
		} catch (ParseException e) {
			log.error("No se pudo parsear la fecha : " + fechaString, e);
			fecha = new Date();
		}
		return fecha;
	}
}
