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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class DemoUserDetailsService implements UserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	private SecurityService securityService;

	public DemoUserDetailsService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserBuilder userBuilder = User.builder();
		Optional<Utilisateur> optUtilisateur = securityService.findUserByLoginArgs(username, null);

		if(optUtilisateur.isPresent()) {
			Utilisateur utilisateur = optUtilisateur.get();
			System.out.println(utilisateur.getMotDePasse());
			utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
			System.out.println(utilisateur.getMotDePasse());
			System.out.println(Arrays.toString(utilisateur.getRoles().toArray(new String[0])));
			userBuilder.username(utilisateur.getNomUtilisateur()).password(utilisateur.getMotDePasse()).roles(utilisateur.getRoles().toArray(new String[0]));
		} else {
			throw new UsernameNotFoundException(username);
		}
/*
		if (us.getNomUtilisateur().equals(username)) {
			System.out.println(motDePasse);
			System.out.println(us.getMotDePasse());
			userBuilder.username(us.getNomUtilisateur()).password(us.getMotDePasse()).roles(us.getRoles().toArray(new String[0]));
		} else {
			throw new UsernameNotFoundException(username);
		}*/

		return userBuilder.build();
	}

}
