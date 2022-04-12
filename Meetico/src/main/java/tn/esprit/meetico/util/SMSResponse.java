package tn.esprit.meetico.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SMSResponse {
	 
    private String mobileNumber;
    
    private String text;
 
}
