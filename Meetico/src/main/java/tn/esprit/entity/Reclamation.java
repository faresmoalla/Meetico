package tn.esprit.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class Reclamation implements Serializable   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="idReclamation")  
	private Integer idReclamation; 
	
	private reclamationType type;
	private String description;
	private String picture;
	private String file;
	@Temporal(TemporalType.DATE)
	private Date sendingDate;
	@Temporal(TemporalType.DATE)
	private Date lastModificationDate;
	
	private reclamationPriority priority;
	
	@Temporal(TemporalType.DATE)
	private Date answerDate;
	private Boolean status;
	
	@ManyToOne
    private User user;
	
	
}
