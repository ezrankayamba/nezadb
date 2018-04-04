package tz.co.nezatech.apps.util.nezadb.repository;

import tz.co.nezatech.apps.util.nezadb.model.IData;

public interface DataEventListener<PK extends Object, T extends IData> {
	public void resultAvailable(Object info, int resCode, String message);
}
