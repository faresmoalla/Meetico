package tn.esprit.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	public Long getIdPublication() {
		return idPublication;
	}

	public void setIdPublication(Long idPublication) {
		this.idPublication = idPublication;
	}

	public int getNbrLikes() {
		return nbrLikes;
	}

	public void setNbrLikes(int nbrLikes) {
		this.nbrLikes = nbrLikes;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public User getUserr() {
		return userr;
	}

	public void setUserr(User userr) {
		this.userr = userr;
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

	private int nbrLikes;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="publications")
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
}
