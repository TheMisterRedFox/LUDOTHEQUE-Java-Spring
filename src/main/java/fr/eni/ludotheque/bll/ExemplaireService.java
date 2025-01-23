package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;
import java.util.List;

public interface ExemplaireService extends ICrudService<Exemplaire> {
    List<Exemplaire> findByJeu(Jeu jeu);
    List<Exemplaire> findAllLouable();
}
