package tn.esprit.meetico.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.meetico.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/common/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources",
				"/configuration/security", "/swagger-ui.html", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().httpBasic().and()
//		.authorizeRequests().anyRequest().authenticated().and()
		.csrf().disable();
	
//		http.authorizeRequests()
//			.antMatchers("/user/signIn").permitAll()
//			.antMatchers("/user/updateUser").permitAll()
//			.antMatchers("/user/assignPictureToUser").permitAll();

//		http.authorizeRequests()
//			.antMatchers("/request/**").hasAuthority("ENTREPRENEUR")
//			.antMatchers("/user/signUp").hasAnyAuthority("ENTREPRENEUR")
//			.antMatchers("/user/removeUser").hasAuthority("ENTREPRENEUR")
//			.antMatchers("/user/retrieveSortedUsers").hasAuthority("ENTREPRENEUR")
//			.antMatchers("/user/searchForUsers").hasAuthority("ENTREPRENEUR")
//			.antMatchers("/user/approvePendingAccount").hasAuthority("EMPLOYEE")
//		.anyRequest().authenticated();
					
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
