package py.gov.senatics.asistente.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

	private static final DateFormat utilDateFormatter = new SimpleDateFormat(
			"dd-MM-yyyy");
	private static final DateFormat sqlDateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static java.sql.Date utilDateToSqlDate(java.util.Date uDate)
			throws ParseException {

		return java.sql.Date.valueOf(sqlDateFormatter.format(uDate));
	}

	public static java.util.Date sqlDateToutilDate(java.sql.Date sDate)
			throws ParseException {

		return utilDateFormatter.parse(utilDateFormatter.format(sDate));
	}

}
