package tn.esprit.meetico.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DestionationVisitorsCount implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	
	private Integer id;
	
	private String detination;
	
	private int visitnbr;
	
	public DestionationVisitorsCount(String detination, int visitnbr) {
	    this.detination = detination;
	    this.visitnbr = visitnbr;

	  }

}
