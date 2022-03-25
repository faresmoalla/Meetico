package tn.esprit.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;



import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Note {
	
    private String subject;
	private String content;
    private Map<String, String> data;
    private String image;
	public Note(String subject, String content, Map<String, String> data) {
		super();
		this.subject = subject;
		this.content = content;
		this.data = data;
	}
    
  
}