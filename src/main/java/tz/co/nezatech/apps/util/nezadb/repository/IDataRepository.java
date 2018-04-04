package tz.co.nezatech.apps.util.nezadb.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import tz.co.nezatech.apps.util.nezadb.model.Status;

public interface IDataRepository<T extends Object> {
	public RowMapper<T> getRowMapper();

	public String getOrderBy();

	public void setOrderBy(String orderBy);

	public List<T> getAll();

	public List<T> getAll(String column, Object value);

	public List<T> search(String value);
	public List<T> search(Map<String, Object> searchFileters);

	public PreparedStatement searchCriteria(String value, Connection conn);
	public PreparedStatement searchCriteria(Map<String, Object> searchFileters, Connection conn);

	public List<T> getAllNullable(String column, Boolean whereNull);

	public Status create(final T entity);

	public T findById(long id);

	public Status update(Integer id, T entity);

	public Status update(T entity);

	public Status delete(long id);

	public Status deleteLinked(long id);

	public String sqlFindAll();

	public String sqlFindById();

	public PreparedStatement psCreate(T entity, Connection conn);

	public PreparedStatement psUpdate(T entity, Connection conn);

	public PreparedStatement psUpdateByKey(T entity, Connection conn);

	public PreparedStatement psDelete(long id, Connection conn);

	public PreparedStatement psDeleteLinked(long id, Connection conn);

	public JdbcTemplate getJdbcTemplate();

	public NamedParameterJdbcTemplate getNamedParamsJdbcTemplate();

	public T schema();

	public boolean updateOnDuplicate();

	public Status onSave(T entity, Status status);

	public List<T> onList(List<T> list);

	public Date fromSQLTimestamp(Timestamp timestamp);

	public Timestamp toSQLTimestamp(Date date);

	List<T> search(List<NamedQueryParam> params);
}
