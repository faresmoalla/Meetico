package tn.esprit.meetico.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long requestId;
	
	private Boolean converted;
	
	private String email;
	
	private String firstName;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
		
	private String lastName;
	
	private Long nic;
	
	private Long phoneNumber;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private User sender;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;
		
	@Enumerated(EnumType.STRING)
	private Status status;

}
