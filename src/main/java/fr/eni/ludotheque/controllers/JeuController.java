package fr.eni.ludotheque.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.ludotheque.bll.GenreService;
import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Jeu;

@RequestMapping("/jeux")
@Controller
public class JeuController {

	private JeuService jeuService;
	private GenreService genreService;
	
	public JeuController(JeuService jeuService, GenreService genreService) {
		this.jeuService = jeuService;
		this.genreService = genreService;
	}
	
	@ModelAttribute("jeu")
	public Jeu createJeu() {
		return new Jeu();
	}
	
	@RequestMapping(path={"/", ""})
	public String afficherListeJeux(Model modele){
		
		modele.addAttribute("jeux", jeuService.findAll());
		return "jeu/liste-jeux";
	}
	
	@GetMapping(path= {"/ajouter"})
	public String afficherFormulaireAjoutJeu(Model modele) {
		modele.addAttribute("genres", genreService.findAll());
		return "jeu/formulaire-jeux";
	}
	
	@GetMapping(path= {"/{noJeu}"})
	public String afficherFicheJeu(@PathVariable(name="noJeu") int noJeu, Model modele){
		
		Optional<Jeu> jeu = jeuService.findById(noJeu);
		
		if(jeu.isPresent()) {
			modele.addAttribute("jeu", jeu.get());
		} else {
			modele.addAttribute("jeu", null);
		}
		
		
		return "jeu/fiche-jeu";
	}
	
	@GetMapping(path= {"/supprimer/{noJeu}"})
	public String suprimerClient(@PathVariable(name="noJeu") int noJeu, Model modele){
		
		jeuService.delete(noJeu);
		return "redirect:/jeux";
	}
	
}
