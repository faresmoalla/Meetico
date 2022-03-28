package tn.esprit.meetico.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import tn.esprit.model.FileDb;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable  {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int idEvent;
	//@NotBlank(message = "title is mandatory")
	private String title;
	//@NotBlank(message = "description is mandatory")
	private String description;
	//@NotBlank(message = "location is mandatory")
	private String location ;
	
	
	
	private String img ; 
	
	
	
	
	
	@Enumerated(EnumType.STRING)
	private EventKind enentkind;
	private Date dateEvent ; 
	//@Min(value = 10)
	//@Max(value=999)
	private int capacity;
	public int getIdEvent() {
		return idEvent;
	}

	@ManyToOne
	@JsonIgnore
	private User user;
	

	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<User> users;
	
//	@OneToOne(mappedBy = "event")
//	private FileDb file;
//	
//	
//	public FileDb getFile() {
//		return file;
//	}
//
//	public void setFile(FileDb file) {
//		this.file = file;
//	}
	
	
}
