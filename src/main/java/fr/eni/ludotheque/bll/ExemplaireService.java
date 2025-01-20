package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;

import java.util.List;
import java.util.Optional;

public interface ExemplaireService extends ICrudService<Exemplaire> {
    List<Exemplaire> findByJeu(Jeu jeu);
}
