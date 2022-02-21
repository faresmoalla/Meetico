package tn.esprit.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long idUtilisateur;
	
			private String nom;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	@JsonIgnore
	private Set<Comment> comments;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="userr")
	@JsonIgnore
	private Set<Publication> publications ;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="utilis")
	@JsonIgnore
	private Set<PostLike> Likes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="utilis")
	@JsonIgnore
	private Set<PostDislike> dislikes;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Trip> trips;
}
