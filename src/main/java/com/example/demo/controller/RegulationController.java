package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exporttoexcel.ExportExpiredRegulations;
import com.example.demo.exporttoexcel.ExportRegulations;
import com.example.demo.models.Regulation;
import com.example.demo.models.RegulationDTO;
import com.example.demo.models.RegulationType;
import com.example.demo.models.Vendor;
import com.example.demo.service.RegulationHistoryService;
import com.example.demo.service.RegulationService;
import com.example.demo.service.RegulationTypeService;
import com.example.demo.service.VendorService;

@RestController
@RequestMapping("regulation")
public class RegulationController {

	@Autowired
	HttpSession sess;

	@Value("${spring.application.name}")
	private String contextPath;

	@Value("${upload.dir}")
	private String uploadPath;

	private RegulationService regulationserv;
	private VendorService vendserv;
	private RegulationTypeService regtypeserv;
	private RegulationHistoryService reghistserv;

	public RegulationController(RegulationService regulationserv, VendorService vendserv,
			RegulationTypeService regtypeserv, RegulationHistoryService reghistserv) {
		super();
		this.regulationserv = regulationserv;
		this.vendserv = vendserv;
		this.regtypeserv = regtypeserv;
		this.reghistserv = reghistserv;
	}

	@PostMapping("/")
	public ResponseEntity<Regulation> saveRegulation(@RequestParam String regulation_name,
			@RequestParam String regulation_description, @RequestParam String regulation_frequency,
			@RequestParam Integer regulation_type_id, @RequestParam String regulation_issued_date,
			@RequestParam String next_renewal_date,

			@RequestParam MultipartFile file, HttpServletRequest request) {
		// Handle the uploaded file and other data here
		// For example, save the file to a local directory

		sess = request.getSession();

		Regulation regulate = new Regulation();
		regulate.setRegulation_name(regulation_name);
		regulate.setRegulation_description(regulation_description);
		regulate.setRegulation_frequency(regulation_frequency);
		regulate.setRegulation_issued_date(regulation_issued_date);
		regulate.setNext_renewal_date(next_renewal_date);
		RegulationType regtype = regtypeserv.getRegulationTypeById(regulation_type_id);

		regulate.setRegulationtype(regtype);

		// Vendor vend =
		// vendserv.getVendorById(Integer.parseInt(""+sess.getAttribute("vendor_id")));
		Vendor vend = vendserv.getVendorById(Integer.parseInt("" + sess.getAttribute("vendor_id")));
		regulate.setVendor(vend);

		Regulation reg = regulationserv.saveRegulation(regulate, file);
		if (reg != null) {

			return new ResponseEntity<Regulation>(reg, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Regulation>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Using the DTO class
	@GetMapping("/")
	public ResponseEntity<List<RegulationDTO>> getAllRegulations(HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.err.println("Auth Obj " + auth.toString());

		List<Regulation> reglist = regulationserv.getAllRegulations();

		List<RegulationDTO> regulationDTOs = new ArrayList<>();

		for (Regulation regulation : reglist) {
			RegulationDTO rdto = new RegulationDTO();
			rdto.setRegulation_id(regulation.getRegulation_id());
			rdto.setRegulation_name(regulation.getRegulation_name());
			rdto.setRegulation_description(regulation.getRegulation_description());
			rdto.setRegulation_frequency(regulation.getRegulation_frequency());
			rdto.setRegulation_issued_date(regulation.getRegulation_issued_date());
			rdto.setNext_renewal_date(regulation.getNext_renewal_date());
			rdto.setFile_name(regulation.getFile_name());
			rdto.setFile_path(regulation.getFile_path());
			rdto.setVendor(regulation.getVendor());
			rdto.setRegulation_type(regulation.getRegulationtype());

			regulationDTOs.add(rdto);

		}
		if (reglist.size() > 0) {
			return new ResponseEntity<List<RegulationDTO>>(regulationDTOs, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<RegulationDTO>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Regulation> getRegulationById(@PathVariable Integer id) {
		Regulation regulation = regulationserv.getRegulationById(id);
		if (regulation != null) {
			return new ResponseEntity<Regulation>(regulation, HttpStatus.OK);
		} else {
			return new ResponseEntity<Regulation>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/vendor/{id}")
	public ResponseEntity<List<Regulation>> getAllRegulationsByVendorId(@PathVariable Integer id) {
		List<Regulation> reglist = regulationserv.getAllRegulationsByVendorId(id);

		reglist.stream().forEach(e -> System.err.println(e.toString()));
		if (reglist.size() > 0)
			return new ResponseEntity<List<Regulation>>(reglist, HttpStatus.OK);
		else
			return new ResponseEntity<List<Regulation>>(HttpStatus.NO_CONTENT);
	}

	// This method will return the regulation of a particular vendor by vendor ID
	// and regulation ID
	@GetMapping("/vendor/{id}/regulation/{rid}")
	public ResponseEntity<List<Regulation>> getRegulationsByVendorIdAndRegulationId(@PathVariable Integer id,
			@PathVariable Integer rid) {
		List<Regulation> reglist = regulationserv.getRegulationsByVendorIdAndRegulationId(id, rid);
		if (reglist.size() > 0)
			return new ResponseEntity<List<Regulation>>(reglist, HttpStatus.OK);
		else
			return new ResponseEntity<List<Regulation>>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/")
	public ResponseEntity<Regulation> updateRegulationById(@RequestParam String regulation_name,
			@RequestParam String regulation_description, @RequestParam String regulation_frequency,
			@RequestParam Integer regulation_type_id, @RequestParam String regulation_issued_date,
			@RequestParam String next_renewal_date, @RequestParam Integer regulation_id,
			@RequestParam MultipartFile file, HttpServletRequest request) {
		sess = request.getSession();

		Regulation regulate = new Regulation();
		regulate.setRegulation_id(regulation_id);
		regulate.setRegulation_name(regulation_name);
		regulate.setRegulation_description(regulation_description);
		regulate.setRegulation_frequency(regulation_frequency);
		regulate.setRegulation_issued_date(regulation_issued_date);
		regulate.setNext_renewal_date(next_renewal_date);

		RegulationType regtype = regtypeserv.getRegulationTypeById(regulation_type_id);

		regulate.setRegulationtype(regtype);

		Vendor vend = vendserv.getVendorById(Integer.parseInt("" + sess.getAttribute("vendor_id")));
		regulate.setVendor(vend);

		int res = regulationserv.updateRegulation(regulate, file);
		if (res > 0) {
			return new ResponseEntity<Regulation>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Regulation>(HttpStatus.NOT_MODIFIED);
		}
	}

	@GetMapping("/pdf/id/{id}")
	public ResponseEntity<Resource> getPdf(@PathVariable Integer id) {
		try {
			Regulation reg = regulationserv.getRegulationById(id);
			if (reg != null) {
				// Construct the absolute path to the file
				String filePath = reg.getFile_path() + "/" + reg.getFile_name();
				Resource pdfFile = new FileSystemResource(filePath);

				if (!pdfFile.exists()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}

				HttpHeaders headers = new HttpHeaders();
				// String encodedFilename = URLEncoder.encode(reg.getFile_name(), "UTF-8");

				String encodedFilename = reg.getFile_name();
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + encodedFilename + "\"");
				headers.setContentType(MediaType.APPLICATION_PDF); // Set MIME type to PDF

//		            System.out.println("Content-Disposition: " + headers.getFirst(HttpHeaders.CONTENT_DISPOSITION));

				return new ResponseEntity<Resource>(pdfFile, headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/expired/regulation/")
	public ResponseEntity<List<Regulation>> getExpiredRegulations() {

		List<Regulation> expiredList = regulationserv.getExpiredRegulations();

		if (expiredList.size() > 0) {
			return new ResponseEntity<List<Regulation>>(expiredList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Regulation>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/expired/regulation/vendor/{id}")
	public ResponseEntity<List<Regulation>> getExpiredRegulationsByVendorId(@PathVariable Integer id) {

		List<Regulation> expiredList = regulationserv.getExpiredRegulationsByVendorId(id);

		if (expiredList.size() > 0) {
			return new ResponseEntity<List<Regulation>>(expiredList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Regulation>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/export")
	public ResponseEntity<InputStreamResource> exportAllRegulationsToExcel(HttpServletResponse response)
			throws IOException {

		List<Regulation> reglist = regulationserv.getAllRegulations();

		HttpHeaders headers = new HttpHeaders();
		String fname = "Active Regulation List_" + LocalDate.now();

		headers.add(HttpHeaders.CONTENT_DISPOSITION, " attachment; filename=" + fname);

		ExportRegulations regulation = new ExportRegulations(reglist);
		byte[] excelContent = regulation.export(response);

		return ResponseEntity.ok().headers(headers)
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(new InputStreamResource(new ByteArrayInputStream(excelContent)));
	}

	@GetMapping("/export/{id}")
	public ResponseEntity<InputStreamResource> exportAllRegulationsByVendorIdToExcel(HttpServletResponse response,
			@PathVariable Integer id) throws IOException {

		List<Regulation> reglist = regulationserv.getAllRegulationsByVendorId(id);
		System.err.println("exportAllRegulationsByVendorIdToExcel \n" + reglist);

		HttpHeaders headers = new HttpHeaders();
		String fname = "Active Regulation List_" + LocalDate.now();

		headers.add(HttpHeaders.CONTENT_DISPOSITION, " attachment; filename=" + fname);

		ExportRegulations regulation = new ExportRegulations(reglist);
		byte[] excelContent = regulation.export(response);

		return ResponseEntity.ok().headers(headers)
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(new InputStreamResource(new ByteArrayInputStream(excelContent)));
	}

	@GetMapping("/expired/export/{usertype}/{vendorid}")
	public ResponseEntity<InputStreamResource> exportExpiredRegulationsToExcel(HttpServletResponse response,
			@PathVariable Integer usertype, @PathVariable Integer vendorid) throws IOException {
		List<Regulation> reglist = null;
		if (usertype == 1) {
			System.err.println("this is ADMIN");
			reglist = regulationserv.getExpiredRegulations();
		} else {
			System.err.println("this is vendor");
			reglist = regulationserv.getExpiredRegulationsByVendorId(vendorid);
			reglist.stream().forEach(e -> System.err.println(e));
		}

		HttpHeaders headers = new HttpHeaders();
		String fname = "Expired Regulations List-" + LocalDate.now();

		headers.add(HttpHeaders.CONTENT_DISPOSITION, " attachment; filename=" + fname);

		ExportExpiredRegulations regulation = new ExportExpiredRegulations(reglist);
		byte[] excelContent = regulation.export(response);

		return ResponseEntity.ok().headers(headers)
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(new InputStreamResource(new ByteArrayInputStream(excelContent)));
	}

}
