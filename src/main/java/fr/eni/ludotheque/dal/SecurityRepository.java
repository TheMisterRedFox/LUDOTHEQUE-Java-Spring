package fr.eni.ludotheque.dal;

import java.util.Optional;
import fr.eni.ludotheque.bo.Utilisateur;

public interface SecurityRepository {
	
	Optional<Utilisateur> findUserByLoginArgs (String username);
}
