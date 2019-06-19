package com.jm.util;

import java.util.List;
import java.util.Map;

public class Table {
	private String title;
	private int startRow;
	private int startColumn;
	private Object[][] colOpts;
	private List<Map<String,Object>> data;
	public Table(){}
	public Table(String title,int startRow,int startColumn,Object[][] colOpts,List<Map<String,Object>> data){
		this.title = title;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.colOpts = colOpts;
		this.data = data;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getStartColumn() {
		return startColumn;
	}
	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}
	public Object[][] getColOpts() {
		return colOpts;
	}
	public void setColOpts(Object[][] colOpts) {
		this.colOpts = colOpts;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
}
