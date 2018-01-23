package tz.co.nezatech.apps.util.nezadb.repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import tz.co.nezatech.apps.util.nezadb.model.Status;

public abstract class BaseDataRepository<T> implements IDataRepository<T> {
	private String orderBy = "";

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public List<T> getAll() {
		String sql = sqlFindAll() + (orderBy == null ? "" : orderBy);
		System.out.println("Sql: " + sql);
		return onList(getJdbcTemplate().query(sql, getRowMapper()));
	}

	public List<T> getAll(String column, Object value) {
		String sql = sqlFindAll() + " and " + column + "='" + value + "'";
		System.out.println("Sql: " + sql);
		return onList(getJdbcTemplate().query(sql + (orderBy == null ? "" : orderBy), getRowMapper()));
	}

	public List<T> getAllNullable(String column, Boolean whereNull) {
		String sql = sqlFindAll() + " and " + column + (whereNull ? " is null " : "is not null");
		System.out.println("Sql: " + sql);
		return onList(getJdbcTemplate().query(sql + (orderBy == null ? "" : orderBy), getRowMapper()));
	}

	@Override
	public List<T> search(final String value) {
		return onList(getJdbcTemplate().query(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				return searchCriteria(value, conn);
			}
		}, getRowMapper()));
	}

	public Status create(final T entity) {
		Status status = null;
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int res = getJdbcTemplate().update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					return psCreate(entity, conn);
				}

			}, keyHolder);
			int generatedId = keyHolder.getKey().intValue();
			status = res == 1 ? new Status(200, "Successfully created enity " + entity, generatedId)
					: new Status(500, "Failed to creat role " + entity + ". Error msg: No role created");
		} catch (DataAccessException e) {
			if (e.getMessage().contains("Duplicate entry") && updateOnDuplicate()) {
				return this.update(entity);
			} else {
				e.printStackTrace();
				return new Status(500, "Failed to creat role " + entity + ". Error msg: " + e.getMessage());
			}
		}

		if (status.getCode() == 200) {
			return onSave(entity, status);
		} else {
			return status;
		}
	}

	@Override
	public T findById(long id) {
		List<T> entities = onList(getJdbcTemplate().query(sqlFindById(), new Object[] { id }, getRowMapper()));
		if (entities != null && !entities.isEmpty()) {
			return entities.get(0);
		}
		return null;
	}

	@Override
	public Status update(Integer id, final T entity) {
		Status status = null;
		try {
			int res = getJdbcTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					return psUpdate(entity, conn);
				}
			});
			status = res == 1 ? new Status(200, "Successfully updated entity " + entity)
					: new Status(500, "Failed to update role " + entity + ". Error msg: No role created");
		} catch (DataAccessException e) {
			e.printStackTrace();
			return new Status(500, "Failed to update entity " + entity + ". Error msg: " + e.getMessage());
		}

		if (status.getCode() == 200) {
			return onSave(entity, status);
		} else {
			return status;
		}
	}

	@Override
	public Status update(final T entity) {
		Status status = null;
		try {
			int res = getJdbcTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					return psUpdateByKey(entity, conn);
				}
			});
			status = res == 1 ? new Status(200, "Successfully updated entity " + entity)
					: new Status(500, "Failed to update role " + entity + ". Error msg: No role created");
		} catch (DataAccessException e) {
			e.printStackTrace();
			return new Status(500, "Failed to update entity " + entity + ". Error msg: " + e.getMessage());
		}

		if (status.getCode() == 200) {
			return onSave(entity, status);
		} else {
			return status;
		}
	}

	@Override
	public PreparedStatement psUpdateByKey(T entity, Connection conn) {
		PreparedStatement ps = psUpdate(entity, conn);

		return ps;
	}

	@Override
	public Status delete(final long id) {
		try {
			int res = getJdbcTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					return psDelete(id, conn);
				}
			});
			return res == 1 ? new Status(200, "Successfully deleted entity " + id)
					: new Status(500, "Failed to delete entity " + id + ". Error msg: No entity deleted");
		} catch (DataAccessException e) {
			e.printStackTrace();
			return new Status(500, "Failed to delete entity " + id + ". Error msg: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T schema() {
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

	@Override
	public boolean updateOnDuplicate() {
		return false;
	}

	@Override
	public List<T> onList(List<T> list) {
		return list;
	}

}
