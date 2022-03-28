package tn.esprit.meetico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.meetico.entity.Picture;
import tn.esprit.meetico.service.CloudinaryService;
import tn.esprit.meetico.service.PictureService;
import tn.esprit.meetico.util.MessageResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
@CrossOrigin
public class CloudinarController {

	@Autowired
	CloudinaryService cloudinaryService;

	@Autowired
	PictureService pictureService;

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestPart MultipartFile multipartFile) throws IOException {
		BufferedImage bi = ImageIO.read(multipartFile.getInputStream());

		if (bi == null) {
			return new ResponseEntity(new MessageResponse("imagen no válida"), HttpStatus.BAD_REQUEST);
		}
		Map result = cloudinaryService.upload(multipartFile);
		Picture picture = new Picture((String) result.get("original_filename"), (String) result.get("url"),
				(String) result.get("public_id"));
		pictureService.save(picture);

		return new ResponseEntity(new MessageResponse("add successfully"), HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws IOException {
		if (!pictureService.exists(id))

			return new ResponseEntity(new MessageResponse("does not exist"), HttpStatus.NOT_FOUND);
		Picture picture = pictureService.getOne(id).get();
		Map result = cloudinaryService.delete(picture.getPictureId());
		pictureService.delete(id);
		return new ResponseEntity(new MessageResponse("deleted image"), HttpStatus.OK);

	}

}