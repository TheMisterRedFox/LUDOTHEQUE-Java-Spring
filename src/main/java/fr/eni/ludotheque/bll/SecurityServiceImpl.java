package fr.eni.ludotheque.bll;

import java.util.Optional;

import fr.eni.ludotheque.dal.SecurityRepository;
import org.springframework.stereotype.Service;

import fr.eni.ludotheque.bo.Utilisateur;

@Service
public class SecurityServiceImpl implements SecurityService {

	private SecurityRepository securityRepository;

	public SecurityServiceImpl(SecurityRepository securityRepository) {
		this.securityRepository = securityRepository;
	}
	
	@Override
	public Optional<Utilisateur> findUserByLoginArgs(String username) {
		return this.securityRepository.findUserByLoginArgs(username);
	}

}
