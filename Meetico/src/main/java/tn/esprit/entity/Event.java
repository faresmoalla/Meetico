package tn.esprit.entity;

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	private String title;
	private String description;
	private String location ;
	private String img ; 
	@Enumerated(EnumType.STRING)
	private EventKind enentkind;
	private Date dateEvent ; 
	
	@ManyToMany(mappedBy="events", cascade = CascadeType.ALL)
	private Set<User> users;
	
	
}