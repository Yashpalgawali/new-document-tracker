package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exporttoexcel.ExportRegulationHistory;
import com.example.demo.models.RegulationHistory;
import com.example.demo.service.RegulationHistoryService;

@RestController
@RequestMapping("regulationhist")
 
public class RegulationHistoryController {

	private RegulationHistoryService reghistserv;
	
	public RegulationHistoryController(RegulationHistoryService reghistserv) {
		super();
		this.reghistserv = reghistserv;
	}


	@GetMapping("/history/{id}")
	 public ResponseEntity<List<RegulationHistory>> getRegulationHistoryByRegulationId(@PathVariable("id") Integer id) {
		 List<RegulationHistory> reglist = reghistserv.getRegulationHistoryByRegulationId(id);
		 return new ResponseEntity<List<RegulationHistory>>(reglist,HttpStatus.OK);
	 }
	 @GetMapping("/export/history/{id}")
	 public ResponseEntity<InputStreamResource> exportRegulationHistoryToExcel(HttpServletResponse response, @PathVariable Integer id) throws IOException{
		 
		 List<RegulationHistory> reglist = reghistserv.getRegulationHistoryByRegulationId(id); 
		 
		 HttpHeaders headers = new HttpHeaders();
		 String fname = "Regulation History "+LocalDate.now();
		 
		 headers.add(HttpHeaders.CONTENT_DISPOSITION," attachment; filename="+fname);
		 
		 ExportRegulationHistory regulation = new ExportRegulationHistory(reglist);
		 byte[] excelContent = regulation.export(response);
		 
		 return ResponseEntity.ok()
				 .headers(headers)
				 .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				 .body(new InputStreamResource(new ByteArrayInputStream(excelContent)));
	 }
}
