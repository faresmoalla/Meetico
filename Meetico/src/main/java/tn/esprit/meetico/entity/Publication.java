package tn.esprit.meetico.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
	@NotBlank
	@Size(max = 100 , message="max akber 100")
     private String contents;
	
	@Temporal(TemporalType.DATE)
	//@NotEmpty(message = "the content field is required")
	private Date date ;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="publications")
	@JsonIgnore
	private Set<Comment> comments;
	
	@ManyToOne
	@JsonIgnore
	User userr;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="publication")
	@JsonIgnore
	private Set<PostLike> likes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="publication")
	@JsonIgnore
	private Set<PostDislike> dislikes;
	
	
	
	@OneToMany(mappedBy="publication",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<FileDB> files;
	
	
	
}
