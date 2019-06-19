package com.jm.utils.export;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportExcelUtils {

	public static final int STRING_CELL =0;
	public static final int DATE_CELL =1;
	public static final int INT_CELL =2;
	public static final int DOUBLE_CELL =3;
	/**
	 * 导出excel
	 * @author wangys
	 * @date 2015年11月9日
	 * @param resp
	 * @param request
	 * @param filename
	 * @param sheets
	 */
	public static void export(HttpServletResponse resp,HttpServletRequest request,String filename,List<SheetView> sheets){
		OutputStream os = null;
		WritableWorkbook wb = null;
		try {
			String agent = request.getHeader("User-Agent");
			boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
			if (isMSIE) {//IE浏览器
				filename = URLEncoder.encode(filename, "UTF-8");
			} else {//其它浏览器
				filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			}
			resp.setHeader("content-disposition", "attachment;filename="+filename);
			os = resp.getOutputStream();
			wb = Workbook.createWorkbook(os);
			int sheetIndex = 0;
			for (SheetView sheetView : sheets) {
				WritableSheet sheet = wb.createSheet(sheetView.getSheetName(), sheetIndex);
				List<Table> tables = sheetView.getTables();
				for (Table table : tables) {
					ExportExcelUtils.createTable(sheet, table.getTitle(), table.getStartRow(), table.getStartColumn(), table.getColOpts(), table.getData());
				}
				sheetIndex++;
			}
			
			wb.write();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}finally{
			if(wb != null){
				try {
					wb.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * excel 中创建表格
	 * @author wangys
	 * @date 2015年6月11日
	 * @param sheet
	 * @param title 表格标题
	 * @param startRow 表格起始行
	 * @param startColumn 表格起始列
	 * @param colOpts 列配置项 例如：new Object[][]{{"coreEntId","核心企业ID",256*20, ExcelHelper.STRING_CELL},}
	 * 数据key,列描述，列宽度，数据类型
	 * @param data 数据
	 * @return
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public static WritableSheet createTable(WritableSheet sheet,String title,int startRow,int startColumn,Object[][] colOpts,List<Map<String,Object>> data) throws RowsExceededException, WriteException{
		CellView cellView = new CellView();  
	    cellView.setAutosize(true); //设置自动大小
		sheet.mergeCells(startColumn,startRow,startColumn+colOpts.length-1,startRow);
		//标题
		WritableFont wfcNav =new WritableFont(WritableFont.ARIAL,12, WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
        WritableCellFormat wcfN=new WritableCellFormat(wfcNav);
        wcfN.setAlignment(Alignment.CENTRE); //设置水平对齐
		Label label = new Label(startColumn, startRow, title,wcfN);
		sheet.addCell(label);
		sheet.setRowView(startRow, 400, false);
	    if (data == null || data.size() == 0){
	    	return sheet;
	    }
	    
	    //列头
		sheet.setRowView(startRow+1, 400, false);
	    for (int colIndex =0;colIndex < colOpts.length; colIndex++) {
	    	label = new Label(colIndex+startColumn, startRow+1, String.valueOf(colOpts[colIndex][1]));
	    	CellView view = new CellView();
	    	view.setSize(Integer.valueOf(String.valueOf(colOpts[colIndex][2])));
		    sheet.setColumnView(colIndex+startColumn, view);
	    	sheet.addCell(label);
    	}
	    
	    //数据
	    int index = 0;
	    for (Map<String,Object> rowData: data) {
	    	int row = ++index+startRow+1;
	    	sheet.setRowView(row, 400,false);
	    	for (int colIndex =0;colIndex < colOpts.length; colIndex++) {
	    		int col = colIndex+startColumn;
	    		Object obj = rowData.get(colOpts[colIndex][0]);
	    		switch (Integer.parseInt(String.valueOf(colOpts[colIndex][3]))) {
				case ExportExcelUtils.DATE_CELL:
					if(obj != null){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
				        String newdate = sdf.format((Date)obj); 
				        label = new Label(col, row, newdate); 
				        sheet.addCell(label);
					}
					break;
				case ExportExcelUtils.DOUBLE_CELL:
					if(obj != null){
						jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); 
				        jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(nf); 
				        jxl.write.Number n = new jxl.write.Number(col, row, Double.parseDouble(Objects.toString(obj,"0")), wcf); 
				        sheet.addCell(n);
					}
					break;
				case ExportExcelUtils.INT_CELL:
					if(obj != null){
						jxl.write.Number numb = new jxl.write.Number(col, row, (Integer)(obj)); 
				        sheet.addCell(numb);
					}
					break;
				case ExportExcelUtils.STRING_CELL:
					label = new Label(col, row, String.valueOf(rowData.get(colOpts[colIndex][0]))); 
			        sheet.addCell(label);
					break;
				default:
					label = new Label(col, row, String.valueOf(rowData.get(colOpts[colIndex][0]))); 
			        sheet.addCell(label);
					break;
				}
	    	}
		}
	    
		return sheet;
	}

}
