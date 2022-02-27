package tn.esprit.entity;

import java.io.Serializable;
import java.util.Date;

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
public class PostDislike implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idDislike;
	
	
	public Long getIdDislike() {
		return idDislike;
	}

	public void setIdDislike(Long idDislike) {
		this.idDislike = idDislike;
	}

	public Comment getCommentt() {
		return commentt;
	}

	public void setCommentt(Comment commentt) {
		this.commentt = commentt;
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
	Comment commentt;
	
	@ManyToOne
	@JsonIgnore
	Publication publication;
	
	@ManyToOne
	@JsonIgnore
	User utilis;
	
}
