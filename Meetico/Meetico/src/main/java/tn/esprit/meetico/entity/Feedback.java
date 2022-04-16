package tn.esprit.meetico.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback implements Serializable   {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="idFeedback") 
	
	private Integer idFeedback;

	@NotBlank(message="The title must be written\"")
	@Size(max=50)
	private String title;
	

	

	
	@NotBlank(message="The description must be written")
	@Size(max=4000 , message="The total number of characters cannot be exceeded")

	private String description;
	
	
	@Temporal(TemporalType.DATE)

	@DateTimeFormat(pattern = "yyy-MM-dd")
	private Date sendingDate;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private Date lastModificationDate;
	

	
	@Min(value = 1, message = " should not be less than 1")
    @Max(value = 5, message = " should not be greater than 5")
	private Integer stars;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@ManyToMany(mappedBy = "feedbacks")
	
	@ApiModelProperty(value = "This parameter will be ignored", required = false)
	private Set<User> users;
	
	@ManyToOne
	@JsonIgnore
	@ApiModelProperty(value = "This parameter will be ignored", required = false)
	private Trip trip;
	
}
