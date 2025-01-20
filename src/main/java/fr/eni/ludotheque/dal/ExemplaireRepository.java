package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;

import java.util.List;
import java.util.Optional;

public interface ExemplaireRepository extends ICrudRepository<Exemplaire> {
    List<Exemplaire> findByJeu(Jeu jeu);
}
