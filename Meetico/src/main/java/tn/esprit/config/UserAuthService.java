package tn.esprit.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tn.esprit.repository.UserRepository;

@Service
public class UserAuthService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;
/*
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User inDB = userRepository.findByUsername(username);
    if (inDB == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return new AppUser(inDB); 
  }
  */

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	// TODO Auto-generated method stub
	return null;
}
  
}