package com.example.demo.exporttoexcel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.demo.models.Regulation;

public class ExportExpiredRegulations {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Regulation> expiredregulations;
	
	public ExportExpiredRegulations(List<Regulation> expiredregulations) {
		super();
		this.expiredregulations = expiredregulations;
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		
		sheet = workbook.createSheet("Expired_Regulations");
		Row row = sheet.createRow(0);
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(12);
		
		style.setFont(font);
				
		createCell(row, 0, "Sr No.", style);
		createCell(row, 1, "Regulation", style);
		createCell(row, 2, "Description", style);
		createCell(row, 3, "Vendor", style);
	
		createCell(row, 4, "Expired On", style);
	}
	
	
	private void createCell(Row row,int columnCount,Object value,CellStyle style)
	{
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);

		if(value instanceof Integer) {
			cell.setCellValue((Integer) value);
		}
		else if(value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}
		else if(value instanceof Long) {
			cell.setCellValue((Long) value);
		}
		else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}


	private void writeDataLines()
	{
		int rowCount=1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();

		font.setBold(false);
		font.setFontHeight(12);
		int sr=1;
		for(Regulation regulation : expiredregulations )
		{
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, sr++ , style);
			createCell(row, columnCount++, regulation.getRegulation_name(), style);
			createCell(row, columnCount++, regulation.getRegulation_description(), style);
			createCell(row, columnCount++, regulation.getVendor().getVendor_name(), style);
			 
			createCell(row, columnCount++, regulation.getNext_renewal_date(), style);
		}
	}

	public byte[] export(HttpServletResponse response) throws IOException
	{
		writeHeaderLine();
		writeDataLines();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		workbook.write(bos);
		workbook.close();
		bos.close();
		
		return bos.toByteArray();
	}
	
}
