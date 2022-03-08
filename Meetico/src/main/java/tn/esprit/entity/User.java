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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor

public class User implements Serializable {


	public User(@Size(max = 50) @Email String email, @NotBlank @Size(min = 6, max = 40) String password,
			@NotBlank @Size(min = 3, max = 20) String userName) {
		super();
		this.email = email;
		this.password = password;
		this.username = userName;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long userId;

	@NonNull
	private Boolean active;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<ActivityField> activityFields;

	@NonNull
	private String address;

	@NonNull
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Event> events;

	@NonNull
	private String firstName;

	@NonNull
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@NonNull
	private String lastName;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<Occupation> occupations;

	@NotBlank
	@Size(min = 6)
	private String password;

	@NonNull
	private Long phoneNumber;

	@NonNull
	private String picturePath;

	@JsonIgnore
	@OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<Request> requests;

	@NonNull
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Trip> trips;
	
	@NotBlank
	@Size(min = 3, max = 25)
	private String username;

	private String verificationCode;

}
