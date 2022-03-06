package tn.esprit.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idComment;
		
	//@Size(max = 50 )
	 @NotEmpty(message = "the content field is required")
     private String contents;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	//@NotEmpty(message = "the content field is required")
	private Date date ;
	
	
	
	@ManyToOne
	@JsonIgnore
	User user;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="comment")
	@JsonIgnore
	private Set<PostLike> likes;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="commentt")
	@JsonIgnore
	private Set<PostDislike> dislikes;

	@ManyToOne
	@JsonIgnore
	Publication publications;
	
	
}
