package tn.esprit.meetico.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tn.esprit.meetico.entity.Alert;
import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.Event;
import tn.esprit.meetico.entity.PostDislike;
import tn.esprit.meetico.entity.PostLike;
import tn.esprit.meetico.entity.Publication;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long userId;

	private Boolean active;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<ActivityField> activityFields;

	private String address;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String city;

	private String tel;
	@NonNull
//	@Size(max = 50)
//	@Email
	private String email;

	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Feedback> feedbacks;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	private Set<Feedback> feedbackss;

	@NonNull
	private String firstName;

	@NonNull
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@NonNull
	private String lastName;

	@NonNull
//	@Size(min = 8, max = 60)
	private String password;

	@NonNull
	// @Size(min = 8, max = 8)
	private Long phoneNumber;

	private String picturePath;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<Profession> professions;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	private Set<Reclamation> reclamations;

	@JsonIgnore
	@OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<Request> requests;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private Role role;

	@ManyToMany
	@JsonIgnore
	private Set<Trip> trips;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Trip> tripss;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	private Set<Comment> comments;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "userr")
	@JsonIgnore
	private Set<Publication> publications;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "utilis")
	@JsonIgnore
	private Set<PostLike> Likes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "utilis")
	@JsonIgnore
	private Set<PostDislike> dislikes;

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Event> events;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "utilis")
	@JsonIgnore
	private Set<Alert> alerts;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	private Set<Picture> picture;


	@NonNull
//	@Size(min = 4, max = 25)
	private String username;

	private Integer verificationCode;

}
