package com.jm.util;

import java.util.ArrayList;
import java.util.List;

public class SheetView {
	private String sheetName;
	private List<Table> tables;
	public SheetView(){}
	public SheetView(String sheetName,Table table){
		this.sheetName = sheetName;
		this.tables = new ArrayList<Table>();
		this.tables.add(table);
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
}
