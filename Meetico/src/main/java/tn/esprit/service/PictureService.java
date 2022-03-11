package tn.esprit.service;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.entity.Picture;
import tn.esprit.repository.PictureRepository;

@Service
@Transactional
public class PictureService {
	 @Autowired
	    PictureRepository imagenRepository;

	    public Optional<Picture> getOne(int id){
	        return imagenRepository.findById(id);
	    }

	    public void save(Picture picture){
	        imagenRepository.save(picture);
	    }

	    public void delete(int id){
	        imagenRepository.deleteById(id);
	    }

	    public boolean exists(int id){
	        return imagenRepository.existsById(id);
	    }
	    

}
