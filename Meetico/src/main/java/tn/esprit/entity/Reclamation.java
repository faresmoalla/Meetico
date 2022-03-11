package tn.esprit.entity;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reclamation implements Serializable   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="idReclamation")  
	private Integer idReclamation; 
	//@NotBlank(message="The title must be written")
	//@Size(max=50)
	private String title;
	@Enumerated(EnumType.STRING)
	private reclamationType type;
	//@NotBlank(message="The description must be written")
	//@Size(max=4000 , message="The total number of characters cannot be exceeded")
	private String description;
	

	@Temporal(TemporalType.DATE)
	//@DateTimeFormat(pattern = "yyy-MM-dd")
	//@NotNull(message = "Please provide a date.")
	private Date sendingDate;
	@Temporal(TemporalType.DATE)
	//@DateTimeFormat(pattern = "yyy-MM-dd")
	//@NotNull(message = "Please provide a date.")
	private Date lastModificationDate;
	@Enumerated(EnumType.STRING)
	private reclamationPriority priority;
	//@Size(max=4000 , message="The total number of characters cannot be exceeded")
	private String answerAdmin;

	@Temporal(TemporalType.DATE)
	//@DateTimeFormat(pattern = "yyy-MM-dd")
	private Date answerDate;
	
	private Boolean status;
	
	@ManyToOne
	@JsonIgnore
    private User user;
	
	@OneToOne
	@JsonIgnore
	private Picture picture;
	
	
}
