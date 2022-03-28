package tn.esprit.meetico.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	
	private Integer idTrip;
	
	@NotEmpty(message = "destination is required")
	private String destination;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@NotEmpty(message = "object required")
	@Size(max=40)
	private String object;
	
	/*
	@Lob
    private byte[] image;
	*/
	
	@ManyToMany(mappedBy="trips")
	@JsonIgnore
	private Set<User> users;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
	@JsonIgnore
	private Set<Feedback> feedbacks;
	
	@OneToMany(mappedBy="trip",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<FileDB> files;

}
