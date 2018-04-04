package tz.co.nezatech.apps.util.nezadb.repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tz.co.nezatech.apps.util.nezadb.model.Status;

public interface IDataRepository<T extends Object> {
	/**
	 * 
	 * @param sql
	 * @return a list of entities T
	 * 
	 *         {@code List<T> queryEntinties(String sql) {return
	 *         getJdbcTemplate().query(sql, getRowMapper()); } }
	 * 
	 * 
	 */
	public List<T> queryEntinties(String sql);

	/**
	 * 
	 * @param value
	 * @return a list of entities
	 * 
	 *         {@code List<T> searchEntities(final String value) {
		return getJdbcTemplate().query(new PreparedStatementCreator() {
	 * 
	 * 		@Override public PreparedStatement createPreparedStatement(Connection
	 *         conn) throws SQLException { return searchCriteria(value, conn); } },
	 *         getRowMapper()); }}
	 * 
	 */
	public List<T> searchEntities(final String value);

	/**
	 * 
	 * @param searchFileters
	 * @return
	 * 
	 * 		{@code List<T> searchEntinties(final Map<String, Object> searchFileters) {
		return getJdbcTemplate().query(new PreparedStatementCreator() {
	 * 
	 * 		@Override public PreparedStatement createPreparedStatement(Connection
	 *         conn) throws SQLException { return searchCriteria(searchFileters,
	 *         conn); } }, getRowMapper()); }}
	 */
	public List<T> searchEntinties(final Map<String, Object> searchFileters);

	/**
	 * 
	 * @param entity
	 * @return
	 * 
	 * 		{@code public Status createEntity(final T entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int res = getJdbcTemplate().update(new PreparedStatementCreator() {
	
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException
	 *         { return psCreate(entity, conn); }
	 * 
	 *         }, keyHolder); int generatedId = keyHolder.getKey().intValue();
	 *         Status status = res == 1 ? new Status(200, "Successfully created
	 *         enity " + entity, generatedId) : new Status(500, "Failed to creat
	 *         role " + entity + ". Error msg: No role created"); return status; }}
	 */
	public Status createEntity(final T entity);

	/**
	 * 
	 * @param id
	 * @return
	 * 
	 * 		{@code public T findEntityById(long id) { List<T> entities =
	 *         onList(getJdbcTemplate().query(sqlFindById(), new Object[] { id },
	 *         getRowMapper())); if (entities != null && !entities.isEmpty()) {
	 *         return entities.get(0); } return null; }}
	 */
	public T findEntityById(long id);

	/**
	 * 
	 * @param entity
	 * @return
	 * 
	 * 
	 * 		{@code public Status updateEntity(final T entity) {
		Status status;
		int res = getJdbcTemplate().update(new PreparedStatementCreator() {
	 * 
	 * 		@Override public PreparedStatement createPreparedStatement(Connection
	 *         conn) throws SQLException { return psUpdateByKey(entity, conn); } });
	 *         status = res == 1 ? new Status(200, "Successfully updated entity " +
	 *         entity) : new Status(500, "Failed to update entity " + entity + ".
	 *         Error msg: No entity updated"); return status; }}
	 */
	public Status updateEntity(final T entity);

	/**
	 * 
	 * @param id
	 * @return
	 * 
	 * 		{@code public Status delete(final long id) {
		int res = getJdbcTemplate().update(new PreparedStatementCreator() {
	 * 
	 * 		@Override public PreparedStatement createPreparedStatement(Connection
	 *         conn) throws SQLException { return psDelete(id, conn); } }); return
	 *         res == 1 ? new Status(200, "Successfully deleted entity " + id) : new
	 *         Status(500, "Failed to delete entity " + id + ". Error msg: No entity
	 *         deleted"); }}
	 */
	public Status delete(final long id);

	/**
	 * 
	 * @return
	 * 
	 * 
	 * 		{@code public  Status deleteLinked(final long id) {
		int res = getJdbcTemplate().update(new PreparedStatementCreator() {
	 * 
	 * 		@Override public PreparedStatement createPreparedStatement(Connection
	 *         conn) throws SQLException { return psDeleteLinked(id, conn); } });
	 *         return res >= 1 ? new Status(200, "Successfully deleted entities
	 *         linked to " + id) : new Status(500, "Failed to delete entities linked
	 *         to " + id + ". Error msg: No entity deleted"); }}
	 */
	public Status deleteLinked(final long id);

	public String getOrderBy();

	public PreparedStatement searchCriteria(Map<String, Object> searchFileters, Connection conn);

	public Status create(final T entity);

	public Status update(Integer id, T entity);

	public Status update(T entity);

	public String sqlFindAll();

	public String sqlFindById();

	public PreparedStatement psCreate(T entity, Connection conn);

	public PreparedStatement psUpdate(T entity, Connection conn);

	public PreparedStatement psUpdateByKey(T entity, Connection conn);

	public PreparedStatement psDelete(long id, Connection conn);

	public PreparedStatement psDeleteLinked(long id, Connection conn);

	default public T findById(long id) {
		return findEntityById(id);
	}

	@SuppressWarnings("unchecked")
	default public T schema() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) type;
		Class<T> cls = (Class<T>) (paramType.getActualTypeArguments()[0]);
		try {
			return cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean updateOnDuplicate();

	public Status onSave(T entity, Status status);

	default public List<T> onList(List<T> list) {
		return list;
	}

	default public Timestamp toSQLTimestamp(Date date) {
		Timestamp tsp = null;
		if (date != null)
			tsp = new Timestamp(date.getTime());
		return tsp;
	}

	default public Date fromSQLTimestamp(Timestamp timestamp) {
		Date date = null;
		if (timestamp != null)
			date = new Date(timestamp.getTime());
		return date;
	}

	default public List<T> getAll() {

		String sql = sqlFindAll() + (getOrderBy() == null ? "" : getOrderBy());
		return onList(queryEntinties(sql));
	}

	default public List<T> getAll(String column, Object value) {
		String sql = sqlFindAll() + " and " + column + "='" + value + "'";
		sql += getOrderBy() == null ? " " : getOrderBy();
		return onList(queryEntinties(sql));
	}

	default public List<T> getAllNullable(String column, Boolean whereNull) {
		String sql = sqlFindAll() + " and " + column + (whereNull ? " is null " : "is not null");
		sql += getOrderBy() == null ? " " : getOrderBy();
		return onList(queryEntinties(sql));
	}

	default public List<T> search(final String value) {
		return onList(searchEntities(value));
	}

	default public List<T> search(final Map<String, Object> searchFileters) {
		return onList(searchEntinties(searchFileters));
	}
}
