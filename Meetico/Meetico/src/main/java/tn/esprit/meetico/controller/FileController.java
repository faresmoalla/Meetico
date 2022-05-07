package tn.esprit.meetico.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.annotations.Api;
import tn.esprit.meetico.entity.FileDB;
import tn.esprit.meetico.service.FileStorageService;
import tn.esprit.meetico.util.FileResponse;
import tn.esprit.meetico.util.MessageResponse;
@CrossOrigin
@RestController
@Api(tags = "File Management")
@RequestMapping("/File")
public class FileController {

	@Autowired
	private FileStorageService storageService;

	@PostMapping("/upload/{idPublication}")
	public ResponseEntity<MessageResponse> uploadFile(@RequestPart("file") MultipartFile file,@PathVariable Long idPublication) {
		String message = "";
		
		try {
			storageService.store(file,idPublication);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}
	}
	@CrossOrigin
	@PutMapping("/affecterimage/{idFile}/{idPublication}")
	public void affecterImage(@PathVariable Long idFile,@PathVariable Long idPublication) throws IOException {
		
		 storageService.affecterImage(idFile,idPublication);
	
	}
	
	
	
	
	@PostMapping("/ajoutimage")
	public Long AjoutImage(@RequestPart("file") MultipartFile file) throws IOException {
		
		return storageService.ajoutFile(file);
	
	}
	
	
	
	 @GetMapping("/getFile2/{id}")
	  public FileDB getFile2(@PathVariable Long id) {
	    return storageService.getFile2(id);
	   
	  }
	 @GetMapping("/filesdetail/{id}")
	  public FileDB getFiledetail(@PathVariable Long id) {
	    return storageService.getFile(id);
	   
	  }
	
	
	@DeleteMapping("/delete-file/{id-file}")
	@ResponseBody
	public void deletetrip(@PathVariable("id-file") Long idfile) {
		storageService.deletefile(idfile);
	}

	@GetMapping("/files")
	public ResponseEntity<List<FileResponse>> getListFiles() {
		List<FileResponse> files = storageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/SpringMVC/File/files/")
					.path(dbFile.getId().toString()).toUriString();
			return new FileResponse(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
		FileDB fileDB = storageService.getFile3(id);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.body(fileDB.getData());
	}

	







}