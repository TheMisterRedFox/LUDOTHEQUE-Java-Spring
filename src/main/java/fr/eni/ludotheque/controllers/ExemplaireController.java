package fr.eni.ludotheque.controllers;

import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequestMapping("/exemplaires")
@Controller
public class ExemplaireController {

    private JeuService jeuService;
    private ExemplaireService exemplaireService;

    public ExemplaireController(JeuService jeuService, ExemplaireService exemplaireService) {
        this.jeuService = jeuService;
        this.exemplaireService = exemplaireService;
    }

    @ModelAttribute("exemplaire")
    public Exemplaire createExemplaire() {
        return new Exemplaire();
    }

    @GetMapping(path= {"/ajouter"})
    public String afficherFormulaireAjoutExemplaire(Model modele){

        modele.addAttribute("jeux", jeuService.findAll());
        return "exemplaire/formulaire-exemplaire";
    }

    @PostMapping(path= {"/ajouter"})
    public String ajouterExemplaire(@Valid @ModelAttribute("exemplaire") Exemplaire exemplaire, BindingResult resultat, Model modele, RedirectAttributes redirectAttr){
        Optional<Jeu> jeuAssocie = jeuService.findById(exemplaire.getJeuId());

        if(resultat.hasErrors() || jeuAssocie.isEmpty()) {
            resultat.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            redirectAttr.addFlashAttribute( "org.springframework.validation.BindingResult.exemplaires", resultat);
            redirectAttr.addFlashAttribute("exemplaire", exemplaire);
            return "redirect:/exemplaires/ajouter";
        }

        exemplaire.setJeu(jeuAssocie.get());
        exemplaireService.save(exemplaire);

        return "redirect:/exemplaires/ajouter";
    }

}
