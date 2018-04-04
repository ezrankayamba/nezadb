package tz.co.nezatech.apps.util.nezadb.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import tz.co.nezatech.apps.util.nezadb.model.Status;

public abstract class BaseDataRepository<T> implements IDataRepository<T> {

	public Status create(final T entity) {
		Status status = null;
		try {
			status= createEntity(entity);
		} catch (Exception e) {
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
	public Status update(final T entity) {
		Status status = null;
		try {
			status = updateEntity(entity);
		} catch (Exception e) {
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
	
}
