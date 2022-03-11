package tn.esprit.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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


}
