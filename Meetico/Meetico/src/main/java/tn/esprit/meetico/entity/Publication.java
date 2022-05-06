package tn.esprit.meetico.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publication implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idPublication;
	// @NotEmpty(message = "the content field is required")
	//@NotBlank
	//@Size(max = 100 , message="max akber 100")
     private String contents;
	
	@Temporal(TemporalType.DATE)
	//@NotEmpty(message = "the content field is required")
	private Date date ;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy="publications"
		,orphanRemoval = true)
	@JsonIgnore
	private Set<Comment> comments;
	
	@ManyToOne
	//@JsonIgnore
	User userr;
	
	/// (cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
	@OneToMany(cascade = { CascadeType.ALL,CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="publication",orphanRemoval = true)
	@JsonIgnore
	private Set<PostLike> likes;
	
	@OneToMany(cascade = { CascadeType.ALL,CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="publication",orphanRemoval = true)
	@JsonIgnore
	private Set<PostDislike> dislikes;
	
		
	
	 
	  @OneToMany(cascade = { CascadeType.ALL,CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="publication",orphanRemoval = true)
	//@JsonIgnore
	  @JsonManagedReference
	private Set<FileDB> files;
	
	
	
}
