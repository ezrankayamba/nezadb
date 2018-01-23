package tz.co.nezatech.apps.util.nezadb.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import tz.co.nezatech.apps.util.nezadb.model.Status;

public interface IDataRepository<T extends Object> {
	public RowMapper<T> getRowMapper();

	public String getOrderBy();

	public void setOrderBy(String orderBy);

	public List<T> getAll();

	public List<T> getAll(String column, Object value);

	public List<T> search(String value);

	public PreparedStatement searchCriteria(String value, Connection conn);

	public List<T> getAllNullable(String column, Boolean whereNull);

	public Status create(final T entity);

	public T findById(long id);

	public Status update(Integer id, T entity);

	public Status update(T entity);

	public Status delete(long id);

	public String sqlFindAll();

	public String sqlFindById();

	public PreparedStatement psCreate(T entity, Connection conn);

	public PreparedStatement psUpdate(T entity, Connection conn);

	public PreparedStatement psUpdateByKey(T entity, Connection conn);

	public PreparedStatement psDelete(long id, Connection conn);

	public JdbcTemplate getJdbcTemplate();

	public T schema();

	public boolean updateOnDuplicate();

	public Status onSave(T entity, Status status);

	public List<T> onList(List<T> list);
}
