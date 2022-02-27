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
	public Long getIdComment() {
		return idComment;
	}

	public void setIdComment(Long idComment) {
		this.idComment = idComment;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<PostLike> getLikes() {
		return likes;
	}

	public void setLikes(Set<PostLike> likes) {
		this.likes = likes;
	}

	public Set<PostDislike> getDislikes() {
		return dislikes;
	}

	public void setDislikes(Set<PostDislike> dislikes) {
		this.dislikes = dislikes;
	}

	public Publication getPublications() {
		return publications;
	}

	public void setPublications(Publication publications) {
		this.publications = publications;
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idComment;
	
	
	 @NotEmpty(message = "the content field is required")
     private String contents;
	
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
