package tn.esprit.meetico.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.meetico.entity.FileDB;
import tn.esprit.meetico.entity.Trip;
import tn.esprit.meetico.repository.FileDBRepository;
import tn.esprit.meetico.repository.TripRepository;

@Service
public class FileStorageService {
	
	@Autowired
	private FileDBRepository fileDBRepository;
	
	@Autowired
	private TripRepository tripRepo;

	public FileDB store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		return fileDBRepository.save(FileDB);
	}

	public void deletefile(Long idfile) {
		fileDBRepository.deleteById(idfile);
	}

	public FileDB getFile(Long id) {
		return fileDBRepository.findById(id).orElse(null);
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