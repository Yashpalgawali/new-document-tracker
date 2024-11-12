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

public class ExportRegulations {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Regulation> activeregulations;
	
	public ExportRegulations(List<Regulation> activeregulations) {
		super();
		this.activeregulations = activeregulations;
		workbook = new XSSFWorkbook();
	}
	
	
	private void writeHeaderLine() {
		
		sheet = workbook.createSheet("Active_Regulations");
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
		createCell(row, 4, "Issued Date", style);
		createCell(row, 5, "Next Renewal Date", style);
		createCell(row, 6, "Frequency", style);
		createCell(row, 7, "File", style);
		
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
		for(Regulation regulation :activeregulations )
		{
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, sr++ , style);
			createCell(row, columnCount++, regulation.getRegulation_name(), style);
			createCell(row, columnCount++, regulation.getRegulation_description(), style);
			createCell(row, columnCount++, regulation.getVendor().getVendor_name(), style);
			createCell(row, columnCount++, regulation.getRegulation_issued_date(), style);
			createCell(row, columnCount++, regulation.getNext_renewal_date(), style);
			if(regulation.getRegulation_frequency().equals("1")) {
				createCell(row, columnCount++, "Monthly", style);
			}
			if(regulation.getRegulation_frequency().equals("2")){
				createCell(row, columnCount++, "Quarterly", style);
			}
			if(regulation.getRegulation_frequency().equals("3")) {
				createCell(row, columnCount++, "Yearly", style);
			}
			createCell(row, columnCount++, regulation.getFile_name() , style);
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
