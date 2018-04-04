package tz.co.nezatech.apps.util.nezadb.repository;

import java.util.LinkedList;
import java.util.List;

import tz.co.nezatech.apps.util.nezadb.repository.NamedQueryParam.Operator;

public class QueryBuilder {
	List<NamedQueryParam> params = new LinkedList<>();
	String name;
	Object value;
	Operator op;
	int status = 0;

	public QueryBuilder param() {
		name = null;
		value = null;
		op = Operator.NA;
		status = 1;
		return this;
	}

	public QueryBuilder paramName(String name) {
		checkStarted();
		this.name = name;
		return this;
	}

	private void checkStarted() {
		if (status == 0) {
			throw new UnsupportedOperationException(
					"You have not yet started this new param. Call param() before adding attributes");
		}
	}

	public QueryBuilder paramValue(Object value) {
		checkStarted();
		this.value = value;
		return this;
	}

	public QueryBuilder build(Operator op) {
		checkStarted();
		this.op = op;
		params.add(new NamedQueryParam(name, op, op));
		status = 2;
		return this;
	}

	public List<NamedQueryParam> compileParams() {
		if (status == 1) {
			throw new UnsupportedOperationException(
					"You have not yet build current param. Call build(Operator op) before compiling params");
		}

		return this.params;
	}
}
