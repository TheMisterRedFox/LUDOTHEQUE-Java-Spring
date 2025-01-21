package fr.eni.ludotheque.bll;

import java.util.Optional;

import fr.eni.ludotheque.bo.Utilisateur;

public interface SecurityService {
	
	Optional<Utilisateur> findUserByLoginArgs (String username);
}
