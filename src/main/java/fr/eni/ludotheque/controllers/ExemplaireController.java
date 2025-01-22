package fr.eni.ludotheque.controllers;

import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
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

        modele.addAttribute("body", "exemplaire/formulaire-exemplaire");
        modele.addAttribute("title", "Ajouter un exemplaire");
        modele.addAttribute("jeux", jeuService.findAll());
        return "index";
    }

    @PostMapping(path= {"/enregistrer"})
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

        if(exemplaire.getNoExemplaire() != null){
            return "redirect:/jeux/" + exemplaire.getJeu().getNoJeu();
        }
        return "redirect:/exemplaires/ajouter";
    }

    @GetMapping("/modifier/{noExemplaire}")
    public String getModifierClient(@PathVariable(name="noExemplaire") int noExemplaire, Model modele) {
        Optional<Exemplaire> exemplaireOpt = exemplaireService.findById(noExemplaire);

        if (exemplaireOpt.isPresent()) {
            Exemplaire exemplaire = exemplaireOpt.get();
            Optional<Jeu> jeuAssocie = jeuService.findById(exemplaire.getJeuId());
            modele.addAttribute("exemplaire", exemplaire);

            if(jeuAssocie.isPresent()){
                System.out.println(jeuAssocie.get());
                List<Jeu> jeux = new ArrayList<Jeu>();
                jeux.add(jeuAssocie.get());
                modele.addAttribute("jeux", jeux);
            }

            modele.addAttribute("body", "exemplaire/formulaire-exemplaire");
            modele.addAttribute("title", "Modifier un exemplaire");
            return "index";
        }

        return "redirect:/jeux";
    }

}
