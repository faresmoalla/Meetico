package tn.esprit.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class PostLike implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idLike;
	
	
	
	
	public Long getIdLike() {
		return idLike;
	}


	public void setIdLike(Long idLike) {
		this.idLike = idLike;
	}


	public Comment getComment() {
		return comment;
	}


	public void setComment(Comment comment) {
		this.comment = comment;
	}


	public Publication getPublication() {
		return publication;
	}


	public void setPublication(Publication publication) {
		this.publication = publication;
	}


	public User getUtilis() {
		return utilis;
	}


	public void setUtilis(User utilis) {
		this.utilis = utilis;
	}


	@ManyToOne
	@JsonIgnore
	Comment comment;
	
	
	@ManyToOne
	@JsonIgnore
	Publication publication;
	
	
	@ManyToOne
	@JsonIgnore
	User utilis;
	
	
}
