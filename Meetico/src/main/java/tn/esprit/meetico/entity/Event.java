package tn.esprit.meetico.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public EventKind getEnentkind() {
		return enentkind;
	}

	public void setEnentkind(EventKind enentkind) {
		this.enentkind = enentkind;
	}

	public Date getDateEvent() {
		return dateEvent;
	}

	public void setDateEvent(Date dateEvent) {
		this.dateEvent = dateEvent;
	}
	
	

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

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
