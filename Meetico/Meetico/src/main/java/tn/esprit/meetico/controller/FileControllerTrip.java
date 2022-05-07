package tn.esprit.meetico.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.meetico.entity.FileTrip;
import tn.esprit.meetico.entity.Trip;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.message.ResponseFile;
import tn.esprit.meetico.message.ResponseMessage;
import tn.esprit.meetico.repository.TripRepository;
import tn.esprit.meetico.service.FileStorageServiceTrip;
@CrossOrigin(origins = "http://localhost:4200/",exposedHeaders="Access-Control-Allow-Origin" )
@Slf4j
@RestController
@RequestMapping("/FileTrip")
public class FileControllerTrip {
  @Autowired
  private FileStorageServiceTrip storageService;
  @Autowired
  TripRepository tripRepo;
  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
    String message = "";
    try {
      storageService.store(file);
      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }
  @PostMapping("/uploadf")
  public Long uploadFilef(@RequestPart("file") MultipartFile file) throws IOException {
  
      return storageService.store1(file);
     
      
   
  }
  	@DeleteMapping("/delete-file/{id-file}")
	@ResponseBody
	public void deletefile( @PathVariable("id-file") Long idfile){
		storageService.deletefile(idfile);
  	}
  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/SpringMVC/File/files/")
          .path(dbFile.getId().toString())
          .toUriString();
      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK).body(files);
    
  }
  @GetMapping("/filesdevoyage/{id}")
  public FileTrip getListFilesdevoyage(@PathVariable Integer id) {
    FileTrip files = storageService.getFile(null);
    return files;
}
  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
    FileTrip fileDB = storageService.getFile(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }
  @GetMapping("/filestime/{id}")
  public ResponseEntity<byte[]> getFiletime(@PathVariable Integer id) {
    FileTrip fileDB = storageService.getFiletime(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }
  @GetMapping("/filesdetail/{id}")
  public FileTrip getFiledetail(@PathVariable Long id) {
    return storageService.getFile(id);
    
   
  }
  @GetMapping("/filebytrip/{id}")
  public Trip getFilebytrip(@PathVariable Long id) {
    return storageService.gettripbyfile(id);
  }
//  @GetMapping("/filesByTrip/{id}")
//  public ResponseEntity<List<ResponseFile>> getListFilesByTRip(@PathVariable Integer id) {
//    List<ResponseFile> files = storageService.getAllFilesBytrip(id).map(dbFile -> {
//      String fileDownloadUri = ServletUriComponentsBuilder
//          .fromCurrentContextPath()
//          .path("/SpringMVC/File/files/")
//          .path(dbFile.getId().toString())
//          .toUriString();
//      return new ResponseFile(
//          dbFile.getName(),
//          fileDownloadUri,
//          dbFile.getType(),
//          dbFile.getData().length);
//    }).collect(Collectors.toList());
//    return ResponseEntity.status(HttpStatus.OK).body(files);
//  }
//  
//  /
//  @GetMapping("/filesByTRipp/{id}")
//  @ResponseBody
//  public ResponseEntity<byte[]> getFilebytripp(@PathVariable Integer id) {
//    List<FileTrip> fileDB = storageService.getFileByTrip(id);
//    
//    //for(FileDB f :fileDB) {
//    	//return ResponseEntity.ok()
//    	  //      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getName() + "\"")
//    	    //    .body(f.getData()) ;
//    	
//    //}
//	return ResponseEntity.ok()
//    	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.iterator().next().getName()+ "\"")
//    	        .body(fileDB.iterator().next().getData() ) ;
//  }
//
//  @GetMapping("/filesByTRippp/{id}")
//  @ResponseBody
//  public List<ResponseEntity<byte[]>> getFileBytrippp(@PathVariable Integer id) {
//	  /*
//    List<FileDB> fileDB = storageService.getFileByTrip(id);
//    List<byte[]> b = new ArrayList<byte[]>() ;
//    		//new ArrayList(ResponseEntity<byte[]>);
//    for(FileDB f :fileDB) {
//    	byte[] s=f.getData();
//    	b.add(s);	
//    }
//    return  ResponseEntity.ok()
//	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
//	        .body(b) ;
//  }*/
//	  List<FileTrip> fileDB = storageService.getFileByTrip(id);
//	  List<ResponseEntity<byte[]>> b = new ArrayList<ResponseEntity<byte[]>>();
//	  ResponseEntity<byte[]> s =new ResponseEntity<byte[]>(null,null,HttpStatus.OK);
//	  for(FileTrip f :fileDB) {
//		  s=ResponseEntity.ok()
//					        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getName() + "\"")
//					        .body(f.getData());
//		  b.add(s);
//			        
//	  }
//	  return b;
//}
//  @GetMapping("/filesByTRipppp/{id}")
//  @ResponseBody
//  public ResponseEntity<List<byte[]>> getFileBytripppp(@PathVariable Integer id) {
//	  
//	  
//	    List<FileTrip> fileDB = storageService.getFileByTrip(id);
//	    List<byte[]> b = new ArrayList<byte[]>() ;
//	    		//new ArrayList(ResponseEntity<byte[]>);
//	    for(FileTrip f :fileDB) {
//	    	byte[] s=f.getData();
//	    	b.add(s);	
//	    }
//	    return  ResponseEntity.ok()
//		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
//		        .body(b) ;
//	  }
  
  
}