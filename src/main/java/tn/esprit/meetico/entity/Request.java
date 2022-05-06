package tn.esprit.meetico.entity;

import java.io.Serializable;
import java.util.Date;
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Request implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long requestId;
		
	@NonNull
	private String email;
	
	@NonNull
	private String firstName;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
		
	@NonNull
	private String lastName;
	
	@NonNull
	private Long nic;
	
	@NonNull
	private Long phoneNumber;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User sender;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;
		
	@Enumerated(EnumType.STRING)
	private Status status;

}
