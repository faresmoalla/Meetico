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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long userId;
	
	private Boolean active;
		
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	private String city;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	private Set<Domain> domains;
	
	private String email;
	
	private String firstName;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String lastName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	private Set<Occupation> occupations;
	
	private String password;
	
	private Long phoneNumber;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
	@JsonIgnore
	private Set<Request> request;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	private Set<Reclamation> reclamations;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
	@JsonIgnore
	private Set<Feedback> feedbacks;
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Trip> trips;
	
	
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
	private Set<Event> events;
	
//	@Enumerated(EnumType.STRING)
//	private Role role;
	
	private String street;
	
	private String username;
	
	private Integer zipCode;
	
}

