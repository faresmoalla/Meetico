package tn.esprit.meetico.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.meetico.entity.FileDB;
import tn.esprit.meetico.entity.Publication;

import tn.esprit.meetico.repository.FileDBRepository;
import tn.esprit.meetico.repository.PublicationRepository;


@Service
public class FileStorageService {
	
	@Autowired
	private FileDBRepository fileDBRepository;
	
//	@Autowired
//	private TripRepository tripRepo;
@Autowired
PublicationRepository  publciationRepo;
	public FileDB store(MultipartFile file,Long idPublicaiton) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		
		Publication publication = publciationRepo.findById(idPublicaiton).orElse(null);
		FileDB.setPublication(publication);
		
		return fileDBRepository.save(FileDB);
	}
	
	public Long ajoutFile(MultipartFile file) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		
		//Publication publication = publciationRepo.findById(idPublicaiton).orElse(null);
		//FileDB.setPublication(publication);
		
		 fileDBRepository.save(FileDB);
		 return FileDB.getId();
		
	}
	public void deletefile(Long idfile) {
		fileDBRepository.deleteById(idfile);
	}
	
	public void affecterImage(Long idFile,Long idPublicaiton) {
		Publication publication = publciationRepo.findById(idPublicaiton).orElse(null);
		FileDB filedb = fileDBRepository.findById(idFile).orElse(null);
		
		
		
		filedb.setPublication(publication);
		fileDBRepository.save(filedb);
		
		
		
		
		}
	
public FileDB getFile2(Long id) {
		
		//Publication publication = publciationRepo.findById(idPublicaiton).orElse(null);
		return fileDBRepository.findById(id).orElse(null);
		
		
	}

public FileDB getFile3(Long id) {
	
	
	return fileDBRepository.findById(id).orElse(null);
	
	
}
	public FileDB getFile(Long idPublicaiton) {
		
		Publication publication = publciationRepo.findById(idPublicaiton).orElse(null);
		return fileDBRepository.GetImageByPub(idPublicaiton);	
	}

	public Stream<FileDB> getAllFiles() {
		return fileDBRepository.findAll().stream();
	}

//	public Stream<FileDB> getAllFilesBytrip(Integer id) {
//		Trip t = tripRepo.findById(id).orElse(null);
//		return t.getFiles().stream();
//	}
//
//	public List<FileDB> getFileByTrip(Integer id) {
//		Trip t = tripRepo.findById(id).orElse(null);
//		return t.getFiles();
//	}
}