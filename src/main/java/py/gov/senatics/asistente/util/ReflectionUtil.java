package py.gov.senatics.asistente.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.collection.PersistentBag;
import org.hibernate.collection.PersistentSet;
import py.gov.senatics.asistente.domain.BaseEntity;
import py.gov.senatics.asistente.exception.RegistroException;

public class ReflectionUtil {

	static Logger log = Logger.getLogger(ReflectionUtil.class);

	public static void main(String[] args) {

		configuracionBasicaLog4j();
	}

	public static Object callGetter(Object bean, String fieldName)
			throws IllegalStateException {

		Method m = null;
		Object ret = null;
		try {
			if (fieldName.indexOf(".") != -1) {
				ret = PropertyUtils.getNestedProperty(bean, fieldName);
			} else {
				m = getReadMethod(bean.getClass(), fieldName);
				ret = m.invoke(bean, new Object[] {});
			}
		} catch (Exception e) {
			// por ahora s�lo me interesa el valor, cualquier otra excecpci�n la
			// wrappeo
			throw new IllegalStateException(e);
		}
		return ret;
	}

	public static String callGetterDynamic(Object bean, String campo,
			List<Integer> parametros) {

		String nombreMetodo = "get" + firstLetterToUppercase(campo);
		String ret = "_SIN_VALOR_";
		Class<?>[] parameterTypes = new Class<?>[parametros.size()];
		Object[] paramsArray = parametros.toArray();
		int i = 0;
		for (Iterator<?> iterator = parametros.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			parameterTypes[i++] = object.getClass();
		}
		try {
			Method m = bean.getClass().getMethod(nombreMetodo, parameterTypes);
			// log.debug("A ejecutar: " + m.getName() + " (" + parametros +
			// ")");
			ret = (String) m.invoke(bean, paramsArray);
		} catch (Exception e) {
			throw new IllegalStateException("No se pudo obtener el m�todo : "
					+ nombreMetodo, e);
		}
		return ret;
	}

	public static void callSetter(Object bean, String fieldName, Object valor)
			throws IllegalStateException {

		Method m = null;
		try {
			if (fieldName.indexOf(".") > 0) {
				PropertyUtils.setNestedProperty(bean, fieldName, valor);
			} else {
				m = getWriteMethod(bean.getClass(), fieldName);
				m.invoke(bean, new Object[] { valor });
			}
		} catch (Exception e) {
			// por ahora s�lo me interesa llamar al getter, cualquier otra
			// excepci�n la wrappeo
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 
	 */
	public static void configuracionBasicaLog4j() {

		Properties conf = new Properties();
		conf.put("log4j.rootLogger", "debug, myAppender");
		conf.put("log4j.appender.myAppender",
				"org.apache.log4j.ConsoleAppender");
		conf.put("log4j.appender.myAppender.layout",
				"org.apache.log4j.SimpleLayout");
		PropertyConfigurator.configure(conf);
	}

	public static String describe(Object objeto) {

		return describe(objeto, true);
	}

	/**
	 * 
	 * Describe la clase y la instancia con el formato nombrePropiedad
	 * (tipoDeDato) : valor
	 * 
	 * @param objeto
	 * @param conHash
	 *            Indica si se imprimen el "de facto" toString ni los hashcodes
	 * @return
	 */
	public static String describe(Object objeto, Boolean conHash) {

		String nombre = objeto.getClass().getSimpleName();
		String nombreClase = objeto.getClass().getName();
		StringBuffer ret = new StringBuffer();
		StringBuffer sbNullValues = new StringBuffer("{");
		PropertyDescriptor[] descs;
		int i = 0;
		try {
			ret.append("<").append(nombre);
			if (conHash)
				ret.append(" hashCode:").append(objeto.hashCode());
			ret.append(" >");
			descs = obtenerPropertyDescriptors(objeto.getClass());
			for (PropertyDescriptor pd : descs) {
				i++;
				if (!pd.getName().equalsIgnoreCase("class")) {
					String stringValue = null;
					String nombrePropiedad = pd.getName();
					Method readMethod = pd.getReadMethod();
					Method writeMethod = pd.getWriteMethod();
					Object val = null;
					if (readMethod != null && writeMethod != null)
						val = readMethod.invoke(objeto, new Object[] {});
					if (val != null) {
						if (val instanceof BaseEntity) {
							// Si la propiedad es de tipo Lazy no intentamos
							// describir
							// para evitar el LazyInitializationException...
							if (objeto instanceof BaseEntity) {
								ManyToOne m2o = getManyToOneAnnotation(
										(BaseEntity) objeto, nombrePropiedad);
								if (m2o != null
										&& m2o.fetch() != FetchType.LAZY) {
									stringValue = describeBaseEntity(conHash,
											(BaseEntity) val);
								} else {
									stringValue = objeto.getClass()
											.getSimpleName()
											+ "."
											+ nombrePropiedad + "(LAZY)";
								}
							} else {
								stringValue = describeBaseEntity(conHash,
										(BaseEntity) val);
							}
						} else if (val instanceof PersistentBag
								|| val instanceof PersistentSet) {
							// Esto daba un StackOverflow
							// con los algunos mapeos ManyToMany
							stringValue = val.getClass().getSimpleName()
									+ " hascode: " + val.hashCode();
						} else
							stringValue = val.toString();
						ret.append("{");
						ret.append(nombrePropiedad);
						ret.append("=");
						ret.append(stringValue);
						ret.append("}");
						if (i < (descs.length - 1))
							ret.append("; ");

					} else {
						sbNullValues.append(nombrePropiedad);

						sbNullValues.append(",");
					}
				}
			}
			if (sbNullValues.length() > 1) { // quiere decir que hay valores
												// nulos
				sbNullValues.append(" = null}");
				ret.append(sbNullValues);
			}
			ret.append("; Class: ").append(nombreClase);
			ret.append("</").append(nombre).append(">");
		} catch (Exception e) {
			// Si pasa algo raro, se retorna el del super
			ret = null;
		}
		if (ret == null) {
			return null;
		} else {
			return ret.toString();
		}
	}

	public static String firstLetterToUppercase(String campo) {

		StringBuffer ret = new StringBuffer();
		ret.append(campo.substring(0, 1).toUpperCase());
		ret.append(campo.substring(1));
		return ret.toString();
	}

	public static Class<?> getClassForField(Object bean, String fieldName) {

		Class<?> ret = null;
		try {
			PropertyDescriptor pd = obtenerPropertyDescriptor(bean.getClass(),
					fieldName);
			ret = pd.getPropertyType();
		} catch (Exception e) {
			ret = Object.class;
		}
		return ret;
	}

	public static Field getDeclaredField(String fieldName, Class<?> resultClass) {

		Field ret = null;
		Class<?> clazz = resultClass;
		if (clazz != null) {
			while (!Object.class.equals(clazz) && ret == null) {
				try {
					ret = clazz.getDeclaredField(fieldName);
				} catch (SecurityException e) {
					ret = null;
					break;
				} catch (NoSuchFieldException e) {
					ret = null;
					clazz = clazz.getSuperclass();
				} catch (NullPointerException npe) {
					ret = null;
					break;
				}
			}
		}
		return ret;
	}

	public static String[] getFieldList(Class<?> clazz)
			throws IllegalStateException {

		ArrayList<String> lista = new ArrayList<String>();
		PropertyDescriptor[] descs = obtenerPropertyDescriptors(clazz);
		for (PropertyDescriptor pd : descs) {
			if (pd.getReadMethod() != null && pd.getWriteMethod() != null)

				lista.add(pd.getName());

		}
		return lista.toArray(new String[0]);
	}

	/**
	 * 
	 * Devuelve la lista de campos read/write del tipo elementType que no son de
	 * tipo Collection
	 * 
	 * @param value
	 * @param elementType
	 * @return
	 */
	public static List<String> getFieldsType(Class<?> clazz,
			Class<?> elementType) {

		List<String> ret = new ArrayList<String>();
		List<String> camposNoEncontrados = new ArrayList<String>();
		String[] fields = getFieldList(clazz);
		for (String fieldName : fields) {
			try {
				Field campo = clazz.getDeclaredField(fieldName);
				if (!Collection.class.isAssignableFrom(campo.getType())) {
					Method readMethod = getReadMethod(clazz, fieldName);
					Method writeMethod = getWriteMethod(clazz, fieldName);
					if (elementType.isAssignableFrom(campo.getType())
							&& readMethod != null && writeMethod != null) {
						// log.debug("agregando: " + fieldName);
						ret.add(fieldName);
					}
				}
			} catch (Exception e) {
				camposNoEncontrados.add(fieldName);
				// ret = new ArrayList<String>();
			}
		}

		return ret;
	}

	public static List<String> getFieldsTypeCollection(Object value,
			Class<?> elementType) {

		List<String> ret = new ArrayList<String>();
		String[] fields = getFieldList(value.getClass());
		for (String fieldName : fields) {
			try {
				Field campo = value.getClass().getDeclaredField(fieldName);
				if (Collection.class.isAssignableFrom(campo.getType())) {
					ParameterizedType type = (ParameterizedType) campo
							.getGenericType();
					Class<?> tipo = (Class<?>) type.getActualTypeArguments()[0];
					Object instanciaTipo = tipo.newInstance();
					if (elementType.isAssignableFrom(instanciaTipo.getClass())) {
						// log.debug("Es descendiente de: " +
						// elementType.getName());
						ret.add(fieldName);
					}
				}
			} catch (Exception e) {
				// log.error("No se pudo obtener la lista de campos.");
				ret = new ArrayList<String>();
			}
		}
		return ret;
	}

	public static String getGetterMethod(String fieldName) {

		return "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1, fieldName.length());
	}

	public static Method getMethod(Object bean, String nombre,
			Class<?> ... params) {

		Method ret = null;
		try {
			ret = bean.getClass().getMethod(nombre, params);
		} catch (Exception e) {
			log.debug("No se pudo obtener el m�todo", e);
			ret = null;
		}
		return ret;
	}

	/**
	 * Retorna el metodo solicitado, pero consume la excepcion.
	 * 
	 * @param bean
	 * @param nombre
	 * @param params
	 * @return
	 */
	public static Method getMethodQuiet(Object bean, String nombre,
			Class<?> ... params) {

		Method m = null;
		try {
			m = bean.getClass().getMethod(nombre, params);
		} catch (Exception e) {
			// consumimos la excepcion
		}
		return m;
	}

	public static List<Method> getPublicMethods(Class<?> clazz) {

		List<Method> ret = new ArrayList<Method>();
		Method[] metodos = clazz.getDeclaredMethods();
		for (int i = 0; i < metodos.length; i++) {
			Method method = metodos[i];
			int mods = method.getModifiers();
			if (mods == Modifier.PUBLIC
					|| (mods == (Modifier.PUBLIC | Modifier.ABSTRACT))) {
				ret.add(method);
			}
		}
		return ret;
	}

	public static List<String> getPublicMethodsName(Class<?> clazz,
			boolean withParameters) {

		List<String> ret = new ArrayList<String>();
		List<Method> metodosPublicos = getPublicMethods(clazz);
		for (Method method : metodosPublicos) {
			int mods = method.getModifiers();
			if (mods == Modifier.PUBLIC
					|| (mods == (Modifier.PUBLIC | Modifier.ABSTRACT))) {
				StringBuffer mDesc = new StringBuffer(method.getName());
				if (withParameters) {
					Class<?>[] params = method.getParameterTypes();
					mDesc.append("(");
					for (int j = 0; j < params.length; j++) {
						Class<?> class1 = params[j];
						mDesc.append(class1.getSimpleName());
						if (j < (params.length - 1))
							mDesc.append(",");
					}
					mDesc.append(")");
				}
				ret.add(mDesc.toString());
			}
		}
		return ret;
	}

	public static Method getReadMethod(Class<?> clazz, String fieldName)
			throws IllegalStateException, NoSuchFieldException {

		PropertyDescriptor desc = obtenerPropertyDescriptor(clazz, fieldName);
		return desc.getReadMethod();
	}

	/**
	 * Obtiene un valor para el tipo de dato. La clase debe tener un constructor
	 * que reciba un String como par�metro
	 * 
	 * @param tipo
	 * @param valor
	 * @return
	 */
	public static Object getValue(String tipo, Object valor) {

		Object ret = null;
		try {
			Constructor<?> constructor = Class.forName(tipo).getConstructor(
					String.class);
			ret = constructor.newInstance(valor);
		} catch (Exception e) {
			log.error("No se pudo instanciar: " + tipo + " con el valor: "
					+ valor);
		}
		return ret;
	}

	public static Method getWriteMethod(Class<?> clazz, String fieldName)
			throws IllegalStateException, NoSuchFieldException {

		PropertyDescriptor desc = obtenerPropertyDescriptor(clazz, fieldName);
		return desc.getWriteMethod();
	}

	public static Object invokeMethod(Method metodo, Object bean,
			Object ... parametros) {

		Object ret = null;
		try {
			ret = metodo.invoke(bean, parametros);
		} catch (Exception e) {
			ret = null;
			log.debug("No se pudo invocar el metodo", e);
		}
		return ret;
	}

	public static PropertyDescriptor obtenerPropertyDescriptor(Class<?> clazz,
			String fieldName) throws IllegalStateException,
			NoSuchFieldException {

		PropertyDescriptor ret = null;
		PropertyDescriptor[] props = obtenerPropertyDescriptors(clazz);
		for (PropertyDescriptor desc : props) {
			if (desc.getName().equals(fieldName)) {
				ret = desc;
			}
		}
		if (ret == null) {
			throw new NoSuchFieldException("No se encuentra el campo: "
					+ fieldName + " en " + clazz.getName());
		}
		return ret;
	}

	/**
	 * 
	 * 
	 * @param destino
	 * @return
	 * @throws IntrospectionException
	 */
	public static PropertyDescriptor[] obtenerPropertyDescriptors(
			Class<?> destino) throws IllegalStateException {

		// Method ret = null;
		BeanInfo info;
		PropertyDescriptor[] ret;
		Comparator<PropertyDescriptor> comparator = new Comparator<PropertyDescriptor>() {

			@Override
			public int compare(PropertyDescriptor o1, PropertyDescriptor o2) {

				int ret = -1;
				// hack para que siempre muestre primero el "id"
				if (o1.getName().equals("id")) {
					ret = -1;
				} else if (o2.getName().equals("id")) {
					ret = 1;
				} else {
					ret = o1.getName().compareTo(o2.getName());
				}
				return ret;
			}
		};
		try {
			info = Introspector.getBeanInfo(destino);
			ret = info.getPropertyDescriptors();
			Arrays.sort(ret, comparator);
			return ret;
		} catch (IntrospectionException e) {
			throw new IllegalStateException(e);
		}
	}

	public static Object read(Object fuente, String fieldName)
			throws IllegalStateException {

		try {
			Method m = getReadMethod(fuente.getClass(), fieldName);
			Object o = m.invoke(fuente, new Object[] {});
			return o;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static Object read(PropertyDescriptor property, Object object)
			throws IllegalStateException {

		try {
			return property.getReadMethod().invoke(object, new Object[] {});
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static Map<Integer, String> salidaComandoPorLinea(String cmdline) {

		Map<Integer, String> ret = new HashMap<Integer, String>();
		try {
			Integer nroLinea = 1;
			String line;
			Process p = Runtime.getRuntime().exec(cmdline);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = input.readLine()) != null) {
				ret.put(nroLinea, line);
				nroLinea = new Integer(nroLinea + 1);
			}
			input.close();
		} catch (Exception err) {
			log.error("Fallo la ejecuci�n del comando:" + cmdline);
		}
		return ret;
	}

	/**
	 * Verifica que TODA la lista de campos tengan alg�n valor distinto de null
	 * cargado O si son String que no est�n vac�os
	 * 
	 * @param oferente
	 * @param camposAControlar
	 * @return
	 */
	public static boolean tieneValoresNoVacios(Object entity,
			String ... camposAControlar) {

		boolean ret = false;
		String[] campos = ReflectionUtil.getFieldList(entity.getClass());
		for (String campo : campos) {
			Object valorCargado = ReflectionUtil.callGetter(entity, campo);
			if (valorCargado == null
					|| ((valorCargado instanceof String && isBlank(valorCargado)))) {
				ret = true;
			}
			if (ret)
				break;
		}
		return ret;
	}

	public static boolean isBlank(Object valorCargado) {

		boolean blank = false;
		if (valorCargado != null)
			blank = valorCargado.toString().trim().length() == 0;
		return blank;
	}

	public static void write(PropertyDescriptor property, Object object,
			Object value) throws IllegalStateException {

		try {
			property.getWriteMethod().invoke(object, value);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static String getCurrentMethodName() {

		boolean doNext = false;
		String ret = null;
		StackTraceElement[] e = Thread.currentThread().getStackTrace();
		for (StackTraceElement s : e) {
			ret = s.getMethodName();
			if (doNext) {
				// log.logDebug(ret);
				break;
			}
			doNext = ret.equals("getCurrentMethodName");
		}
		return ret;
	}

	/**
	 * @param conHash
	 * @param val
	 * @return
	 */
	private static String describeBaseEntity(Boolean conHash,
			BaseEntity baseEBean) {

		String stringValue;
		if (conHash) {
			stringValue = baseEBean.toString(true);
		} else {
			// Aca se supone que entra para obtener un
			// cacheId
			// entonces verifico que tenga algún valor y le
			// pido su hash como "valor"
			if (hasSomeValue(baseEBean)) {
				stringValue = baseEBean.getClass().getSimpleName() + "-hash: "
						+ describe(baseEBean, false);
			} else {
				stringValue = baseEBean.getClass().getName() + "-id: "
						+ baseEBean.getId();
			}
		}
		return stringValue;
	}

	/**
	 * Verifica que el bean tenga algún valor cargado distinto de null
	 * 
	 * @param bean
	 * @return
	 */
	public static boolean hasSomeValue(Object bean) {

		boolean ret = false;
		if (bean != null) {
			try {
				String[] campos = getFieldList(bean.getClass());
				for (String c : campos) {
					Object obj = callGetter(bean, c);
					if (obj instanceof BaseEntity) {
						ret |= hasSomeValue(obj);
					} else {
						// TODO - afeltes : Ver esto cómo se trataría en las
						// consultas
						// por string vacío
						// porque del form viene así, no viene null
						// Si es un string y está vacío, considero que no tiene
						// ningún
						// valor
						ret |= noEsNullVacioNiCollection(obj);
					}
					if (ret) // Si hay algún valor != null, ya retorno true
						break;
				}
			} catch (Exception e) {
				ret = false;
			}
		}
		return ret;
	}

	public static boolean noEsNullVacioNiCollection(Object value) {

		boolean ret = (value != null && !(value instanceof Collection) && !(value instanceof String))
				|| (value instanceof String && !isBlank(value));
		return ret;
	}

	public static ManyToOne getManyToOneAnnotation(BaseEntity entity,
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

	public static void camposVaciosANull(BaseEntity<Long> entity)
			throws RegistroException {

		List<String> camposString = ReflectionUtil.getFieldsType(
				entity.getClass(), String.class);
		for (String nombreCampo : camposString) {
			String value = (String) ReflectionUtil.callGetter(entity,
					nombreCampo);
			if (BolsaUtil.isBlank(value)) {
				ReflectionUtil.callSetter(entity, nombreCampo, null);
			} else {// Convierto los campos String que tengan algun valor, a
					// LATIN
				value = BolsaUtil.convertToCharset(value, "UTF-8");
				ReflectionUtil.callSetter(entity, nombreCampo, value);
			}
		}
	}

}
