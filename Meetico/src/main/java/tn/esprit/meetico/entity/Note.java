package tn.esprit.meetico.entity;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Note {

	@NonNull
	private String subject;
	
	@NonNull
	private String content;
	
	@NonNull
	private Map<String, String> data;
	
	private String image;

}