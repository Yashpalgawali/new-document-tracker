package com.example.demo.exporttoexcel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.demo.models.RegulationHistory;

public class ExportRegulationHistory {

	private XSSFWorkbook workbook;
	
	private XSSFSheet sheet;
	
	private List<RegulationHistory> reghistory;

	public ExportRegulationHistory(  List<RegulationHistory> reghistory) {
		super();
		 workbook = new XSSFWorkbook();
		this.reghistory = reghistory;
	}

	private void writeHeaderLine() {
		
		sheet = workbook.createSheet("Regulation History");
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
		createCell(row, 6, "Status", style);
		createCell(row, 7, "Frequency", style);
		createCell(row, 8, "File", style);
		
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
		for(RegulationHistory regulation : reghistory )
		{
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, sr++ , style);
			createCell(row, columnCount++, regulation.getHist_regulation_name() , style);
			createCell(row, columnCount++, regulation.getHist_regulation_description(), style);
			createCell(row, columnCount++, regulation.getVendor().getVendor_name() , style);
			createCell(row, columnCount++, regulation.getHist_regulation_issued_date(), style);
			createCell(row, columnCount++, regulation.getHist_next_renewal_date(), style);
			createCell(row, columnCount++, getRegulationStatus(regulation.getHist_next_renewal_date()), style);
			
//			if(getRegulationStatus(regulation.getHist_next_renewal_date()).equals("Active")) {
// 
//				font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
//				createCell(row, columnCount++, getRegulationStatus(regulation.getHist_next_renewal_date()), style);
//			}
//			
//			if(getRegulationStatus(regulation.getHist_next_renewal_date()).equals("Expired")) {
//				
//				font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
//				createCell(row, columnCount++, getRegulationStatus(regulation.getHist_next_renewal_date()), style);
//			}
						
			
			if(regulation.getHist_regulation_frequency().equals("1")) {
				createCell(row, columnCount++, "Monthly", style);
			}
			if(regulation.getHist_regulation_frequency().equals("2")){
				createCell(row, columnCount++, "Quarterly", style);
			}
			if(regulation.getHist_regulation_frequency().equals("3")) {
				createCell(row, columnCount++, "Yearly", style);
			}
			createCell(row, columnCount++, regulation.getHist_file_name() , style);
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
	
	private String getRegulationStatus(String date) {
		
		LocalDate today = LocalDate.now();
		char[] charArray = date.toCharArray();
		
		String d="",m="",y="",ndate="";
		 
		for(int i=0;i<charArray.length;i++)
		{
			if(charArray[i]!='-') {
				if(i<2) {
					d = d+""+charArray[i]; 
				}
				if(i>2 && i<5) {
					m = m+""+charArray[i];
				}
				if(i>5) {
					y = y+""+charArray[i];
				}
			}
		}
		ndate = y+"-"+m+"-"+d;
 
		LocalDate localDate1 = LocalDate.parse(ndate); 
		long difference = ChronoUnit.DAYS.between(localDate1, today);
		if(difference>0) {
			return "Expired";
		}
		else if(difference<0) {
			return "Active";
		}
		else if(difference==0) {
			return "Due Today";
		}
		else 
			return "";		 
	}

}
