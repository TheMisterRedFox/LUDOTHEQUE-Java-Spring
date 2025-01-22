package fr.eni.ludotheque.security;

import fr.eni.ludotheque.bll.SecurityService;
import fr.eni.ludotheque.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Optional;

@Component
public class LudotequeUserDetailsService implements UserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	private SecurityService securityService;

	public LudotequeUserDetailsService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserBuilder userBuilder = User.builder();
		Optional<Utilisateur> optUtilisateur = securityService.findUserByLoginArgs(username);

		if(optUtilisateur.isPresent()) {
			Utilisateur utilisateur = optUtilisateur.get();
			System.out.println(Arrays.toString(utilisateur.getRoles().toArray(new String[0])));
			userBuilder.username(utilisateur.getNomUtilisateur()).password(utilisateur.getMotDePasse()).roles(utilisateur.getRoles().toArray(new String[0]));
		} else {
			throw new UsernameNotFoundException(username);
		}

		return userBuilder.build();
	}

}
