package tn.esprit.meetico.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Snippet {
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Feedback> feedbacks;

}

