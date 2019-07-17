package com.brt.bref.common.constant;

public class TableColumn {
	private String key;
	private String tableColumn;
	private int type;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTableColumn() {
		return tableColumn;
	}
	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public TableColumn(String key, String tableColumn, int type) {
		super();
		this.key = key;
		this.tableColumn = tableColumn;
		this.type = type;
	}
}
