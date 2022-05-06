package tn.esprit.meetico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.meetico.entity.Picture;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.service.CloudinaryService;
import tn.esprit.meetico.service.PictureService;
import tn.esprit.meetico.util.MessageResponse;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "*" )
@RestController
@RequestMapping("/cloudinary")
@Api(tags = "Upload Api Cloudinary ")

public class CloudinarController {

	@Autowired
	CloudinaryService cloudinaryService;

	@Autowired
	PictureService pictureService;
	
	
	
    
	@PostMapping("/upload")
	@ApiOperation(value = "Upload Picture")
	public Integer upload(@RequestPart MultipartFile multipartFile) throws IOException {
	
		Map result = cloudinaryService.upload(multipartFile);
		Picture picture = new Picture((String) result.get("original_filename"), (String) result.get("url"),
				(String) result.get("public_id"));
		pictureService.save(picture);

		return picture.getId();
	}
    
    
	@ApiOperation(value = "Delete Picture")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws IOException {
		if (!pictureService.exists(id))

			return new ResponseEntity(new MessageResponse("does not exist"), HttpStatus.NOT_FOUND);
		Picture picture = pictureService.getOne(id).get();
		Map result = cloudinaryService.delete(picture.getPictureId());
		pictureService.delete(id);
		return new ResponseEntity(new MessageResponse("deleted image"), HttpStatus.OK);

	}
	@ApiOperation(value = "Detail Picture")
	@GetMapping("/getPicture/{id}")
	
	public Optional<Picture> getPictureById(@PathVariable(name="id") int idPicture) {
		Optional<Picture> P = pictureService.getOne(idPicture);
	    
		return P;
	}
	
	

}
