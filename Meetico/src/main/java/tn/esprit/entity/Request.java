package tn.esprit.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;
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

public class Request implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long requestId;
		
	private String email;
	
	private String firstName;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String lastName;
	
	private Long NIC;
		
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;
	
	@ManyToOne
	User sender;

}
