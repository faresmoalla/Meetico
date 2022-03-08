package tn.esprit.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AuthUser {
	
	private String email;

	@NotBlank
	@Size(min = 6)
	private String password;
	
	@NotBlank
	@Size(min = 3, max = 25)
	private String userName;

}