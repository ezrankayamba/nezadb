package tz.co.nezatech.nezadb.repository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import tz.co.nezatech.nezadb.model.IData;
import tz.co.nezatech.nezadb.model.NamedQueryParam;


/**
 * 
 * @author nkayamba
 *
 * @param <PK>
 * @param <T>
 * 
 *
 */
public interface IDataRepository<PK extends Object, T extends IData> {
	public IDataRepository<PK, T> getlistener();

	public void create(T e);

	public void create(List<T> list);

	public void update(T e);

	public void update(List<T> list);

	public void delete(T e);

	public void delete(List<T> list);

	public List<T> query(List<NamedQueryParam> filters);

	public T query(PK id);

	default public String tableName() {
		Class<?> type = getType();
		return String.format(Locale.ENGLISH, "tbl_%s", type.getSimpleName().replaceAll("([A-Z])", "_$1").toLowerCase());
	}

	default public String querySql() {
		return String.format(Locale.ENGLISH, "SELECT * FROM %s", tableName());
	}

	default public String deleteSql(Class<T> type) {
		return String.format(Locale.ENGLISH, "DELETE FROM %s", tableName());
	}

	default public String updateSql() {
		return String.format(Locale.ENGLISH, "UPDATE %s", tableName());
	}

	default public Map<String, Class<?>> fieldTypes() {
		Class<?> type = getType();
		Map<String, Class<?>> map = new LinkedHashMap<String, Class<?>>();
		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			if (!f.getName().equalsIgnoreCase("schema")) {
				map.put(f.getName(), f.getType());
			}
		}
		return map;
	}

	default public Class<?> getType() {
		Type superclassType = getClass().getGenericSuperclass();
		if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
			return null;
		}
		Type[] types = ((ParameterizedType) superclassType).getActualTypeArguments();
		return types[1].getClass();
	}
}
