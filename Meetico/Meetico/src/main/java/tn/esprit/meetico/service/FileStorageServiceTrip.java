package tn.esprit.meetico.service;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.meetico.entity.FileTrip;
import tn.esprit.meetico.entity.Trip;
import tn.esprit.meetico.repository.FileDBRepositoryTrip;
import tn.esprit.meetico.repository.TripRepository;

@Service
public class FileStorageServiceTrip {
	Long idf;
  @Autowired
  private FileDBRepositoryTrip fileDBRepo;
  @Autowired
  TripRepository tripRepo;
  public FileTrip store(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    FileTrip FileDB = new FileTrip(fileName, file.getContentType(), file.getBytes());
    return fileDBRepo.save(FileDB);
  }public Long store1(MultipartFile file) throws IOException {
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    FileTrip FileDB = new FileTrip(fileName, file.getContentType(), file.getBytes());
	    fileDBRepo.save(FileDB);
	    return FileDB.getId();
	  }
  public void deletefile(Long idfile) {
	  FileTrip f =fileDBRepo.findById(idfile).orElse(null);
	  f.setTrip(null);
	  fileDBRepo.delete(f);
  }
  public FileTrip getFile(Long id) {
    return fileDBRepo.findById(id).orElse(null);
  }
  public Trip gettripbyfile(Long id) {
	  FileTrip f = fileDBRepo.findById(id).orElse(null);
	  return f.getTrip();
  }
  public FileTrip getFiletime(Integer id) {
	    Trip t = tripRepo.findById(id).orElse(null);
	     
	    	
			return t.getFiles();
	    
	  }
  
  public Stream<FileTrip> getAllFiles() {
    return fileDBRepo.findAll().stream();
  }
}/*
  public FileTrip getAllFilesBytrip(Integer id) {
	  Trip t =tripRepo.findById(id).orElse(null);
	    return t.getFiles().stream();
	  }
  public List<FileTrip> getFileByTrip(Integer id) {
	  Trip t =tripRepo.findById(id).orElse(null);
	    return t.getFiles();
	  }
}*/