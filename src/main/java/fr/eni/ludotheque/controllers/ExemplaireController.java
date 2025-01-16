package fr.eni.ludotheque.controllers;

import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/exemplaire")
@Controller
public class ExemplaireController {

    private JeuService jeuService;

    public ExemplaireController(JeuService jeuService) {
        this.jeuService = jeuService;
    }

    @ModelAttribute("exemplaire")
    public Exemplaire createExemplaire() {
        return new Exemplaire();
    }

    @GetMapping(path= {"/ajouter"})
    public String afficherFormulaireAjoutExemplaire(Model modele){

        List<Jeu> jeux = jeuService.findAll();
        return "exemplaire/formulaire-exemplaire";
    }

}
