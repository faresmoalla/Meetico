package tn.esprit.meetico.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long userId;

	private Boolean active;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<ActivityField> activityFields;

	private String address;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@NonNull
	private String email;

	@NonNull
	private String firstName;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<User> followers;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	private List<User> following;

	@NonNull
	@Enumerated(EnumType.STRING)
	private Gender gender;

	private Date lastSeen;
	
	@NonNull
	private String lastName;

	@NonNull
	private String password;

	@NonNull
	private Long phoneNumber;

	private String picture;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Profession> professions;

	@JsonIgnore
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
	private Set<Request> requests;

	@Enumerated(EnumType.STRING)
	private Role role;

	@NonNull
	private String username;

	private Integer verificationCode;

}
