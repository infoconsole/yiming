package com.mitix.yiming;

public class ResponseObject<T> extends Response{
	
	private	long total;
	
	private T rows;
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public T getRows() {
		return rows;
	}

	public void setRows(T rows) {
		this.rows = rows;
	}

}
