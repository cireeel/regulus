package com.example;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // Disables CSRF for your testing API
			.authorizeRequests()
				.antMatchers("/api/**").permitAll() // Public endpoint
				.anyRequest().authenticated()          // Everything else requires sign-in
			.and()
			.formLogin().disable()                    // Disables the redirect to sign-in page
			.httpBasic().disable();                   // Disables the browser pop-up prompt
 
		return http.build();
	}
}