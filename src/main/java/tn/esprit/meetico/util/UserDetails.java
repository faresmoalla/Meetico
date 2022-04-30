package tn.esprit.meetico.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tn.esprit.meetico.entity.User;

@Getter
@AllArgsConstructor
public class UserDetails {

	private User user;

	private String accessToken;
}
