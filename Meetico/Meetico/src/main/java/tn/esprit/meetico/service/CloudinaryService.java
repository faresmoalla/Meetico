package tn.esprit.meetico.service;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {
	
	Cloudinary cloudinary;
	private Map<String, String> ValuesMap = new HashMap<>();

	public CloudinaryService() {
		ValuesMap.put("cloud_name", "meetico");

		ValuesMap.put("api_key", "764271971918625");

		ValuesMap.put("api_secret", "oy6Vh33BlERdBAj9tkDAqepCvQI");

		cloudinary = new Cloudinary(ValuesMap);

	}

	public Map upload(MultipartFile mulitipartFile) throws IOException {
		File file = convert(mulitipartFile);
		Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		file.delete();

		return result;

	}

	public Map delete(String id) throws IOException {
		Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());

		return result;
	}

	private File convert(MultipartFile multipartFile) throws IOException {
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(multipartFile.getBytes());
		fo.close();
		return file;
	}

}
