package py.gov.senatics.asistente.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Level;
import org.hibernate.annotations.Formula;
import org.slf4j.Logger;
import py.gov.senatics.asistente.exception.RegistroException;

public class BolsaUtil {

	public static final String FECHA_HASTA_SUFIJO = "_hasta";
	@Inject
	static Logger log;
	public static final Map<Class<?>, Comparator<?>> reverseOrdersMap = new HashMap<Class<?>, Comparator<?>>();
	public static final Map<Class<?>, Comparator<?>> ascendingOrdersMap = new HashMap<Class<?>, Comparator<?>>();
	public static final Boolean ORDEN_ASCENDENTE = Boolean.TRUE;
	public static final Boolean ORDEN_DESCENDENTE = Boolean.FALSE;
	public static final Date FECHA_DESDE_DEFAULT;
	// Estos hay que mantener sincronizados con el UIParam
	protected static final String ALGORITHM = "Blowfish";
	private static final String ENCODING = "UTF8";
	// Esto debería guardarse en algún properties para cambiar periódicamente:
	// 16 bytes
	private static byte[] defaultKey = new byte[] { 32, 11, 12, 42, 64, 5, 7,
			8, 9, 99, 98, 97, 96, 95, 94, 93 };
	// Estos hay que mantener sincronizados con el UIParam
	private static final Map<String, byte[]> emailsKeys = new HashMap<String, byte[]>();
	private static final Map<String, Boolean> classFieldTransient = new HashMap<String, Boolean>();
	private static final Map<String, Boolean> classFieldFormula = new HashMap<String, Boolean>();
	public static final Map<String, String> valoresEncriptadosClaveDefault = new HashMap<String, String>();
	static {
		reverseOrdersMap.put(SelectItem.class, new Comparator<SelectItem>() {

			@Override
			public int compare(SelectItem o1, SelectItem o2) {

				return o1.getLabel().compareTo(o2.getLabel()) * -1;
			}
		});

		ascendingOrdersMap.put(SelectItem.class, new Comparator<SelectItem>() {

			@Override
			public int compare(SelectItem o1, SelectItem o2) {

				return o1.getLabel().compareTo(o2.getLabel());
			}
		});

		// Inicializamos la fecha default para los rangos, al 1980/01/01
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.YEAR, 1980);
		FECHA_DESDE_DEFAULT = cal.getTime();

	}

	public static Integer getAnioActual() {

		SimpleDateFormat anioFormat = new SimpleDateFormat("yyyy");
		return Integer.parseInt(anioFormat.format(Calendar.getInstance()
				.getTime()));
	}

	/**
	 * Retorna true si la cadena es nula, vacía, o espacio en blanco.
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String s) {

		return StringUtils.isBlank(s);
	}

	public static boolean isBlank(Object o) {

		boolean ret = true;
		if (o != null) {
			ret = StringUtils.isBlank(o.toString());
			// log.debug(o.toString());
		}
		return ret;
	}

	public static boolean equalsIgnoraMayusculaAcentos(String cadena1,
			String cadena2) {

		boolean ret = false;
		Collator comparador = Collator.getInstance();
		comparador.setStrength(Collator.PRIMARY);
		ret = (comparador.compare(cadena1, cadena2) == 0);
		return ret;
	}

	/**
	 * Retorna un comparator Descendente (reverso) para la clase pasada de
	 * parámetro. Se inicializa en el bloque static de esta clase.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Comparator<?> getReverseOrder(Class<?> clazz) {

		return reverseOrdersMap.get(clazz);
	}

	/**
	 * Retorna un comparator Ascendente para la clase pasada de parámetro. Se
	 * inicializa en el bloque static de esta clase.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Comparator<?> getAscendingOrder(Class<?> clazz) {

		return ascendingOrdersMap.get(clazz);
	}

	public static boolean isTransient(Class<?> clazz, String property) {

		boolean ret = false;
		String key = clazz.getName() + "." + property;
		Boolean isTrans = classFieldTransient.get(key);
		try {
			if (isTrans == null) {
				Field field = clazz.getDeclaredField(property);
				Annotation[] anns = field.getAnnotations();
				for (Annotation ann : anns) {
					// Buscamos entre las anotaciones para esta propiedad, si
					// tiene
					// Transient
					ret = ann instanceof Transient;
					if (ret)
						break;
				}
				classFieldTransient.put(key, ret);
			} else
				ret = isTrans;
		} catch (NoSuchFieldException e) {

			ret = isTransient(clazz.getSuperclass(), property);

			classFieldTransient.put(key, ret);
		}
		// log.debug("Clase: " + clazz.getSimpleName() + " , property: " +
		// property + ", isTransient: " + ret);
		return ret;
	}

	public static boolean isFormula(Class<?> clazz, String property) {

		boolean ret = false;
		String key = clazz.getName() + "." + property;
		Boolean isForm = classFieldFormula.get(key);
		try {
			if (isForm == null) {
				Field field = clazz.getDeclaredField(property);
				Annotation[] anns = field.getAnnotations();
				for (Annotation ann : anns) {
					// Buscamos entre las anotaciones para esta propiedad, si
					// tiene
					// Transient
					ret = ann instanceof Formula;
					if (ret)
						break;
				}
				classFieldFormula.put(key, ret);
			} else
				ret = isForm;
		} catch (NoSuchFieldException e) {

			ret = isFormula(clazz.getSuperclass(), property);

			classFieldFormula.put(key, ret);
		}
		// log.debug("Clase: " + clazz.getSimpleName() + " , property: " +
		// property + ", isTransient: " + ret);
		return ret;
	}

	public static void main(String[] args) {

		// Categoria c = new Categoria();
		// BeanUtil.configuracionBasicaLog4j();
		// String propiedad = "valorAMostrarAUsuario";
		// log.debug(" Es transient: " + BolsaUtil.isTransient(c.getClass(),
		// propiedad));
		// Pac pac = new Pac();
		// for (String a : BeanUtil.getFieldsTypeCollection(pac,
		// Auditable.class))
		// {
		// log.debug(" propiedades collection cascade ALL -> " + a + " : "
		// + BolsaUtil.isOneToManyCascadeTypeALL(pac, a));
		// }
		Long l = 10l;
		System.out.println("padde: " + padNumberWithZeros(l, 8, true));
		System.out.println("Convertido: " + convertToUS_ACII("������������"));
	}

	public static boolean noEsNullVacioNiCollection(Object value) {

		boolean ret = (value != null && !(value instanceof Collection) && !(value instanceof String))
				|| (value instanceof String && !isBlank((String) value));
		return ret;
	}

	public static BolsaUtil instance() {

		return new BolsaUtil();
	}

	public Map<String, Object> getSessionMap() {

		Map<String, Object> ret = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc != null) {
			ExternalContext context = fc.getExternalContext();
			if (context != null) {
				ret = context.getSessionMap();
				if (ret == null)
					log.error("No session map");
			}
		} else {
			log.trace("No faces context map");
			ret = new HashMap<String, Object>();
		}
		return ret;
	}

	public Map<String, Object> getRequestMap() {

		Map<String, Object> reqMap = new HashMap<String, Object>();
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc != null) {
			ExternalContext context = fc.getExternalContext();
			if (context != null)
				reqMap = context.getRequestMap();
		}
		return reqMap;
	}

	public Map<String, Object> getApplicationMap() {

		Map<String, Object> appMap = new HashMap<String, Object>();
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc != null) {
			ExternalContext context = fc.getExternalContext();
			if (context != null)
				appMap = context.getApplicationMap();
		}
		return appMap;
	}

	public static String firstLetterToUppercase(String campo) {

		StringBuffer ret = new StringBuffer();
		ret.append(campo.substring(0, 1).toUpperCase());
		ret.append(campo.substring(1));
		return ret.toString();
	}

	public static boolean isOneToManyCascadeTypeALL(Object entity,
			String property) {

		boolean ret = false;
		try {
			Field field = entity.getClass().getDeclaredField(property);
			Annotation[] anns = field.getAnnotations();
			for (Annotation ann : anns) {
				// Buscamos entre las anotaciones para esta propiedad, si tiene
				// Transient
				ret = ann instanceof OneToMany;
				if (ret) {
					OneToMany o2m = (OneToMany) ann;
					boolean cascadeAll = false;
					for (CascadeType ct : o2m.cascade()) {
						if (ct.equals(CascadeType.ALL)) {// Quiere decir que
															// tiene un
															// cascadetype de
															// tipo ALL
							cascadeAll = true;
							break;
						}
					}
					ret &= cascadeAll;
					break;
				}
			}
		} catch (NoSuchFieldException e) {
			log.trace("No se pudo obtener los annotations para la propiedad: "
					+ property + " de la clase " + entity.getClass().getName());
			ret = false;
		}
		return ret;
	}

	public static String truncateWithSuffix(String aString, int length,
			String suffix) {

		if (aString != null && suffix != null) {
			int longitudReal = aString.length();
			StringBuffer cadenaRet = new StringBuffer();
			if (longitudReal > length) {
				cadenaRet
						.append(aString.substring(0, length - suffix.length()));
				cadenaRet.append(suffix);
				return cadenaRet.toString();
			} else {
				return aString;
			}
		} else
			return "";
	}

	public static ManyToOne getManyToOneAnnotation(Object entity,
			String property) {

		ManyToOne ret = null;
		try {
			Field field = entity.getClass().getDeclaredField(property);
			Annotation[] anns = field.getAnnotations();
			for (Annotation ann : anns) {
				if (ann instanceof ManyToOne) {
					ret = (ManyToOne) ann;
					break;
				}
			}
		} catch (NoSuchFieldException e) {
			// log.debug("No se pudo obtener los annotations para la propiedad: "
			// + property
			// + " de la clase " + entity.getClass().getName());
			ret = null;
		}
		return ret;
	}

	public static String padNumberWithZeros(Number number,
			Integer cantidadZeros, Boolean left) {

		String ret = null;
		if (left)
			ret = StringUtils.leftPad(number.toString(), cantidadZeros, "0");
		else
			ret = StringUtils.rightPad(number.toString(), cantidadZeros, "0");
		return ret;
	}

	public static String leftPadZeros(Number cod, Integer cantidadZeros) {

		return padNumberWithZeros(cod, cantidadZeros, true);
	}

	public static String rightPadZeros(Number cod, Integer cantidadZeros) {

		return padNumberWithZeros(cod, cantidadZeros, false);
	}

	public static String encryptarConPassword(String message, byte[] key) {

		String ret = null;
		if (key.equals(defaultKey)) {
			String cached = valoresEncriptadosClaveDefault.get(message);
			if (cached != null)
				ret = cached;
		}
		if (ret == null) {
			try {
				Cipher c = Cipher.getInstance(ALGORITHM);
				SecretKeySpec k = new SecretKeySpec(key, ALGORITHM);
				c.init(Cipher.ENCRYPT_MODE, k);
				byte[] encrypted = c.doFinal(message.getBytes(ENCODING));
				ret = byteToBase64(encrypted);
				if (log.isTraceEnabled()) {
					log.trace("clave:" + new String(key));
					log.trace("\tplain: " + message);
					log.trace("\tencrypted: " + ret);
				}
				if (key.equals(defaultKey)) {
					log.trace("Usando clave default, se cachea el valor.");
					valoresEncriptadosClaveDefault.put(message, ret);
				}
			} catch (Exception e) {
				log.error("No se pudo encriptar la cadena: " + e.getMessage());
			}
		}
		return ret;
	}

	public static String desencryptarConPassword(String encrypted, byte[] key) {

		return desencryptarConPassword(encrypted, key, false);
	}

	public static String desencryptarConPassword(String encrypted, byte[] key,
			boolean quiet) {

		String ret = null;
		if (!isBlank(encrypted)) {
			if (key.equals(defaultKey)
					&& valoresEncriptadosClaveDefault.containsValue(encrypted)) {
				ret = getKeyByValue(valoresEncriptadosClaveDefault, encrypted);
			} else {
				ret = desencriptarConKeyByte(encrypted, key, quiet);
				if (key.equals(defaultKey))
					valoresEncriptadosClaveDefault.put(ret, encrypted);
				else {
					if (ret == null) {
						// por �ltimo probamos desencriptar con el defaultKey
						// solo
						// Para los reportes que usan sicp:param
						ret = desencriptarConKeyByte(encrypted, defaultKey,
								quiet);
					}
				}
			}
		}
		return ret;
	}

	private static String desencriptarConKeyByte(String encrypted, byte[] key,
			boolean quiet) {

		String ret = null;
		try {
			byte[] rawEnc = base64ToByte(encrypted);
			Cipher c = Cipher.getInstance(BolsaUtil.ALGORITHM);
			SecretKeySpec k = new SecretKeySpec(key, BolsaUtil.ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, k);
			byte[] raw = c.doFinal(rawEnc);
			ret = new String(raw, ENCODING);
			if (log.isTraceEnabled()) {
				log.trace("clave:" + new String(key));
				log.trace("\tencrypted: " + encrypted);
				log.trace("\tdecrypted: " + ret);
			}
		} catch (Exception e) {
			// aca entra mucho cu�ndo se generan los reportes
			// porque los parametros se encriptan con la clave de sesion
			// asi qeu informamos nada m�s de que no se pudo desencriptar
			// para evitar los stack trace
			if (!quiet)
				log.error("No se pudo desencriptar la cadena: " + encrypted);
			if (log.isTraceEnabled()) {
				if (quiet)// solo vuelvo a imprimir si es quiet, porque sino ya
							// se imprime antes
					log.trace("No se pudo desencriptar la cadena: " + encrypted);
				log.trace("\tclave: " + new String(key));
			}
		}
		return ret;
	}

	/**
	 * From a byte[] returns a base 64 representation
	 * 
	 * @param data
	 *            byte[]
	 * @return String
	 * @throws IOException
	 */
	public static String byteToBase64(byte[] data) {

		return Base64Coder.encodeLines(data);
	}

	public static String encrypt(String message) {

		return encryptarConPassword(message, BolsaUtil.defaultKey);
	}

	public static String decrypt(String encrypted) {

		return desencryptarConPassword(encrypted, BolsaUtil.defaultKey);
	}

	/**
	 * From a base 64 representation, returns the corresponding byte[]
	 * 
	 * @param data
	 *            String The base64 representation
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] base64ToByte(String data) throws IOException {

		return Base64Coder.decodeLines(data);
	}

	public static Map<String, byte[]> getEmailsKeys() {

		return emailsKeys;
	}

	public static void setCategoriaLogLevel(String categoria, Level level) {

		org.apache.log4j.Logger.getLogger(categoria).setLevel(level);
	}

	public static void setHibernateLogLevel(String categoria, Level level) {

		log.trace("Hibernate level a: " + level);
		setCategoriaLogLevel("org.hibernate.SQL", level);
		setCategoriaLogLevel("org.hibernate.type", level);
	}

	/**
	 * Convierte la cadena recibida a ISO-8859-1
	 * 
	 * @param s
	 * @return
	 * @throws RegistroException
	 */
	public static String convertToLATIN(String s) throws RegistroException {

		byte[] iso;
		try {
			iso = s.getBytes("ISO-8859-1");
			s = new String(iso, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			throw new RegistroException(
					"ERROR al cambiar la codificación de los caracteres a ISO-8859-1 (LATIN1). "
							+ "UnsupportedEncodingException: " + e.getMessage());
		}
		return s;
	}

	/**
	 * Retorna true si
	 * 
	 * @return
	 */
	public static Boolean isCollectionValuesEmpty(Collection<?> collection) {

		Boolean ret = true;
		if (collection != null && !collection.isEmpty()) {
			for (Object element : collection) {
				ret &= isBlank(element);
				if (!ret)
					break;
			}
		}
		return ret;
	}

	/**
	 * Devuelve la permutación de los criterios para ser usados en sentencias de
	 * like o ilike
	 * 
	 * Por ej. para input: 1, 2, 3 -> %1%2%3% %2%3%1% %1%3%2% %3%2%1% %2%1%3%
	 * %1%3%2% %2%3%1% %3%1%2% %3%1%2% %1%2%3% %3%2%1% %2%1%3%
	 * 
	 * @param criterios
	 * @return
	 */
	public static String getCriteriosCombinados(List<String> criterios) {

		String ret = "";
		if (criterios.size() == 2) {
			ret += "%" + criterios.get(0) + "%" + criterios.get(1) + "%";
			ret += " %" + criterios.get(1) + "%" + criterios.get(0) + "% ";
		} else if (criterios.size() > 2) {
			for (int i = 0; i < criterios.size(); i++) {
				String primero = criterios.get(i);
				List<String> criteriosAPermutar = new ArrayList<String>();
				for (String criter : criterios)
					if (!primero.equals(criter))
						criteriosAPermutar.add(criter);
				String otrosCriterios = getCriteriosCombinados(criteriosAPermutar);
				if (!BolsaUtil.isBlank(otrosCriterios)) {
					String[] otros = otrosCriterios.split(" ");
					for (String otro : otros) {
						if (!BolsaUtil.isBlank(otro)) {
							ret += " %" + primero + otro;
							ret += " " + otro + primero + "%";
						}
					}
				}
			}
		} else if (criterios.size() == 1
				&& !BolsaUtil.isBlank(criterios.get(0))) {
			ret = " %" + criterios.get(0) + "% ";
		}
		return ret;
	}

	public static String encryptarAUnaSolaLineaConPassword(String message,
			byte[] key) {

		String ret = null;
		try {
			Cipher c = Cipher.getInstance(ALGORITHM);
			SecretKeySpec k = new SecretKeySpec(key, ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, k);
			byte[] encrypted = c.doFinal(message.getBytes(ENCODING));
			ret = byteToBase64UnaSolaLinea(encrypted);
		} catch (Exception e) {
			log.error("No se pudo encriptar la cadena: " + e.getMessage());
		}
		return ret;
	}

	public static String desencryptarDeUnaSolaLineaConPassword(
			String encrypted, byte[] key) {

		String ret = null;
		try {
			byte[] rawEnc = base64UnaSolaLineaToByte(encrypted);
			Cipher c = Cipher.getInstance(BolsaUtil.ALGORITHM);
			SecretKeySpec k = new SecretKeySpec(key, BolsaUtil.ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, k);
			byte[] raw = c.doFinal(rawEnc);
			ret = new String(raw, ENCODING);
		} catch (Exception e) {
			log.error("No se pudo desencriptar la cadena" + e.getMessage());
		}
		return ret;
	}

	private static byte[] base64UnaSolaLineaToByte(String encrypted) {

		return Base64Coder.decode(encrypted);
	}

	public static String byteToBase64UnaSolaLinea(byte[] data) {

		return new String(Base64Coder.encode(data));
	}

	public static boolean valueOf(Boolean bool) {

		boolean ret = false;
		if (!isBlank(bool)) {
			ret = Boolean.valueOf(bool);
		}
		return ret;
	}

	public static void habilitarLogSQL() {

		BolsaUtil.setCategoriaLogLevel("org.hibernate.SQL", Level.DEBUG);
		BolsaUtil.setCategoriaLogLevel("org.hibernate.type", Level.TRACE);
	}

	public static void deshabilitarLogSQL() {

		BolsaUtil.setCategoriaLogLevel("org.hibernate.SQL", Level.WARN);
		BolsaUtil.setCategoriaLogLevel("org.hibernate.type", Level.WARN);
	}

	/**
	 * Devuelve un entero positivo, entre 1 y el maxValue
	 * 
	 * @param maxValue
	 * @return
	 */
	public static Integer getRandomInteger(int maxValue) {

		Integer ret = (RandomUtils.nextInt() % maxValue) + 1;
		return ret;
	}

	public static String desencryptarDeUnaSolaLinea(String cadenaEncriptada) {

		return desencryptarDeUnaSolaLineaConPassword(cadenaEncriptada,
				defaultKey);
	}

	public static String encryptarAUnaSolaLinea(String aEncriptar) {

		return encryptarAUnaSolaLineaConPassword(aEncriptar, defaultKey);
	}

	public static byte[] getDefaultKey() {

		return defaultKey;
	}

	public String getRemoteAddress() {

		String ret = "SIN_NRO_IP";
		FacesContext fContext = FacesContext.getCurrentInstance();
		if (fContext != null) {
			ExternalContext context = fContext.getExternalContext();
			if (context != null) {
				HttpServletRequest request = (HttpServletRequest) context
						.getRequest();
				ret = request.getRemoteAddr();
			} else
				log.error("NO HAY ExternalContext para obtener el nro. de ip del request de HTTP");
		}
		return ret;
	}

	public static String convertEncoding(String encoded, String charSetOrigen,
			String charSetDestino) throws RegistroException {

		String decoded = encoded;
		Charset charsetUTF = Charset.forName(charSetDestino);
		Charset charsetISO = Charset.forName(charSetOrigen);
		CharsetEncoder encoder = charsetISO.newEncoder();
		CharsetDecoder decoder = charsetUTF.newDecoder();
		try {
			// Convert a string to ISO-LATIN-1 bytes in a ByteBuffer
			// The new ByteBuffer is ready to be read.
			ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(encoded));
			// Convert ISO-LATIN-1 bytes in a ByteBuffer to a character
			// ByteBuffer and then to a string.
			// The new ByteBuffer is ready to be read.
			CharBuffer cbuf = decoder.decode(bbuf);
			decoded = cbuf.toString();
			log.debug(encoded + " (" + charSetOrigen
					+ ") en charset destino CON CharBuffer: " + charSetDestino
					+ " -> " + decoded);
		} catch (CharacterCodingException e) {
			throw new RegistroException(
					"ERROR al cambiar la codificación de los caracteres a "
							+ charSetOrigen, e);
		}
		return decoded;
	}

	public static Date getFechaHastaViernes() {

		Date ahora = new Date();
		Date viernes2359 = null;
		Calendar cal = Calendar.getInstance();
		int dia_semana = cal.get(Calendar.DAY_OF_WEEK);
		int dias_hasta_viernes = Calendar.FRIDAY - dia_semana;
		Date fechaHastaViernes = null;
		if (dias_hasta_viernes > 0) {
			fechaHastaViernes = FechaUtil.desplazarFecha(ahora, 0, 0, 0,
					dias_hasta_viernes, 0, 0, false);
			cal.setTime(fechaHastaViernes);
		}
		cal.set(Calendar.HOUR_OF_DAY, 17);
		cal.set(Calendar.MINUTE, 00);
		viernes2359 = cal.getTime();
		return viernes2359;
	}

	public static Date getFechaHastaFinMes() {

		Date ahora = new Date();
		Date finMes = null;
		Calendar cal = Calendar.getInstance();
		int dia_mes = cal.get(Calendar.DAY_OF_MONTH);
		int dias_hasta_fin_mes = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
				- dia_mes;
		Date fechaHastaFinMes = null;
		if (dias_hasta_fin_mes > 0) {
			fechaHastaFinMes = FechaUtil.desplazarFecha(ahora, 0, 0, 0,
					dias_hasta_fin_mes, 0, 0, false);
			cal.setTime(fechaHastaFinMes);
		}
		cal.set(Calendar.HOUR_OF_DAY, 17);
		cal.set(Calendar.MINUTE, 00);
		finMes = cal.getTime();
		return finMes;
	}

	public static Date getFechaHastaFinAnio() {

		Date ahora = new Date();
		Date finAnio = null;
		Calendar cal = Calendar.getInstance();
		int dia_anio = cal.get(Calendar.DAY_OF_YEAR);
		int dias_hasta_fin_anio = cal.getActualMaximum(Calendar.DAY_OF_YEAR)
				- dia_anio;
		Date fechaHastaFinAnio = null;
		if (dias_hasta_fin_anio > 0) {
			fechaHastaFinAnio = FechaUtil.desplazarFecha(ahora, 0, 0, 0,
					dias_hasta_fin_anio, 0, 0, false);
			cal.setTime(fechaHastaFinAnio);
			cal.set(Calendar.HOUR_OF_DAY, 17);
			cal.set(Calendar.MINUTE, 00);
			finAnio = cal.getTime();
		}
		return finAnio;
	}

	/**
	 * Convierte la cadena recibida al charset solicitado
	 * 
	 * @param s
	 * @return
	 * @throws RegistroException
	 */
	public static String convertToCharset(String s, String charset)
			throws RegistroException {

		byte[] iso;
		try {
			iso = s.getBytes(charset);
			s = new String(iso, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RegistroException(
					"ERROR al cambiar la codificación de los caracteres a + "
							+ charset + ". UnsupportedEncodingException: "
							+ e.getMessage());
		}
		return s;
	}

	/**
	 * Quita los acentos y la �. El convert a charset directo no convert�a bien,
	 * dejaba el caracter "?"
	 * 
	 * @param s
	 * @param charset
	 * @return
	 * @throws RegistroException
	 */
	public static String convertToUS_ACII(String s) {

		char[] especiales = new char[] { 'á', 'é', 'í', 'o', 'u', 'Á', 'É',
				'Í', 'Ó', 'Ú', 'ñ', 'Ñ' };
		char[] reemplazos = new char[] { 'a', 'e', 'i', 'o', 'u', 'A', 'E',
				'I', 'O', 'U', 'n', 'N' };
		String retorno = s;
		for (int i = 0; i < especiales.length; i++) {
			retorno = retorno.replace(especiales[i], reemplazos[i]);
		}
		return retorno;
	}

	public static String getKeyByValue(Map<String, String> map, String value) {

		for (Entry<String, String> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static String obtenerClaveFija() {

		return bigIntegerFromMD5("aVerQuesaledeEsto102938");
	}

	public static String bigIntegerFromMD5(String cadena) {

		String ret = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] buffer = cadena.getBytes();
			for (int i = 0; i < buffer.length; i++) {
				digest.update(buffer[i]);
			}
			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			// TODO - Ale Feltes : Ver esto de reemplazar con el JCE para poder
			// tener claves más largas
			ret = bigInt.toString(16).substring(0, 16);
		} catch (NoSuchAlgorithmException e) {
			ret = null;
		}
		return ret;
	}
}
