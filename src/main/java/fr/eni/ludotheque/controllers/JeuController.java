package fr.eni.ludotheque.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Genre;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import fr.eni.ludotheque.bll.GenreService;
import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.bo.Jeu;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		modele.addAttribute("jeu", new Jeu());
		return "jeu/formulaire-jeux";
	}

	@PostMapping(path= {"/ajouter"})
	public String ajouterJeu(@Valid @ModelAttribute("jeu") Jeu jeu, BindingResult resultat, Model modele, RedirectAttributes redirectAttr){
		if(resultat.hasErrors()) {

			resultat.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			redirectAttr.addFlashAttribute( "org.springframework.validation.BindingResult.jeu", resultat);
			redirectAttr.addFlashAttribute("jeu", jeu);
			return "redirect:/jeux/ajouter";
		}

		jeu.setGenres(genreService.findMultipleById(jeu.getGenresIdList()));
		jeuService.save(jeu);
		return "redirect:/jeux";
	}

	@GetMapping("/modifier/{noJeu}")
	public String getModifierClient(@PathVariable(name="noJeu") int noJeu, Model modele) {
		Optional<Jeu> jeuOpt = jeuService.findById(noJeu);

		if (jeuOpt.isPresent()) {
			Jeu jeu = jeuOpt.get();
			List<Integer> genresIdList = jeu.getGenres().stream().map(Genre::getNoGenre).collect(Collectors.toList());
			jeu.setGenresIdList(genresIdList);
			modele.addAttribute("genres", genreService.findAll());
			modele.addAttribute("jeu", jeuOpt.get());
			return "jeu/formulaire-jeux";
		}

		return "redirect:/jeux";
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
