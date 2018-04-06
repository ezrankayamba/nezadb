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
public interface IDataRepository<PK, T extends IData> {

	public T create(T e);

	default public void create(List<T> list) {
		throw new UnsupportedOperationException("method create-list not yet supported");
	}

	public T update(T e);

	default public void update(List<T> list) {
		throw new UnsupportedOperationException("method update-list not yet supported");
	}

	default public <RT extends Object> RT execute(Object... anyParams) {
		throw new UnsupportedOperationException("method update-list not yet supported");
	}

	public void delete(PK id);

	default public void delete(List<T> list) {
		throw new UnsupportedOperationException("method delete-list not yet supported");
	}

	default public List<T> query(List<NamedQueryParam> filters) {
		throw new UnsupportedOperationException("method query-list-namedqueryparams not yet supported");
	}

	public List<T> search(String lookup);

	public T query(PK id);

	default public String tableName() {
		Class<?> type = getType();
		return String.format(Locale.ENGLISH, "tbl_%s", type.getSimpleName().replaceAll("([A-Z])", "_$1").toLowerCase());
	}

	default public String querySql() {
		return String.format(Locale.ENGLISH, "SELECT * FROM %s", tableName());
	}

	default public String deleteSql() {
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
