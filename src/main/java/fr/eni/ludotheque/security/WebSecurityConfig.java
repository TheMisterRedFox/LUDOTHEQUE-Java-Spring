package fr.eni.ludotheque.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/jeux/ajouter", "/jeux/modifier/**", "/jeux/supprimer/**", "/jeux/{noJeu}/ajoutExemplaire").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/jeux/enregistrer", "/clients/enregistrer", "/exemplaires/enregistrer").hasRole("ADMIN")
						.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
						.requestMatchers("/", "/jeux/**").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin((form) -> form
						.loginPage("/login")
						.defaultSuccessUrl("/jeux", false)
						.failureUrl("/login?error=true")
						.permitAll()
				)
				.logout((logout) -> logout.permitAll())
				.csrf((csrf) -> csrf
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				);

		return http.build();
	}

	@Bean
	public PasswordEncoder encoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
