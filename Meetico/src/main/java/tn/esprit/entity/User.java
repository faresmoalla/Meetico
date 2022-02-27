package tn.esprit.entity;

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
		
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<ActivityField> activityFields;
	
	private String address;
		
	@Temporal(TemporalType.DATE)
	private Date birthday;
		
	private String email;
	
	private String firstName;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String lastName;
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<Occupation> occupations;
	
	private String password;
	
	private Long phoneNumber;
	
	@OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<Request> request;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String username;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Event> events;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private Set<Trip> trips;
		
}

