package tn.esprit.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

	ADMIN, ENTREPRENEUR, EMPLOYEE, MEMBER;

	@Override
	public String getAuthority() {
		return "ROLE_" + name();
	}

}