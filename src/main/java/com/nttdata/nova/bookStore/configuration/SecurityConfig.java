package com.nttdata.nova.bookStore.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableGlobalMethodSecurity(
		prePostEnabled = true // Pre/Post Annotations
		)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		String adminPassword = encoder.encode("password");
		String userPassword = encoder.encode("user");
		
		auth.inMemoryAuthentication()
				.withUser("admin") // ADMIN
				.password(adminPassword)
				.roles("ADMIN")
				.and()
				.withUser("user") // USER
				.password(userPassword)
				.roles("USER");
	}
	
	
}
