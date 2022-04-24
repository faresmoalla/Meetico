package tn.esprit.meetico.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
public class Picture {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    private String name;
	    private String pictureUrl;
	    private String pictureId;
	    public Picture(String name, String pictureUrl, String pictureId) {
	        this.name = name;
	        this.pictureUrl = pictureUrl;
	        this.pictureId = pictureId;
	    }
	    
@OneToOne(mappedBy = "picture")
private Reclamation reclamation;

@ManyToOne
@JsonIgnore
private User user;



}
