package tn.esprit.meetico.util;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {
	
	@NotBlank
	@Size(min = 5, max = 30, message = "Your username must be between 5 and 30 characters long.")
	private String username;
	
	@NotBlank
    @Size(min = 8, message = "Your password must be at least 8 characters long.")
	private String password;
	
}
