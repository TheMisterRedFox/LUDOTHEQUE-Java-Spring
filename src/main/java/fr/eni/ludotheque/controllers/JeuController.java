package fr.eni.ludotheque.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bo.Exemplaire;
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

@RequestMapping({"/jeux", "/", ""})
@Controller
public class JeuController {

	private JeuService jeuService;
	private GenreService genreService;
	private ExemplaireService exemplaireService;
	
	public JeuController(JeuService jeuService, GenreService genreService, ExemplaireService exemplaireService) {
		this.jeuService = jeuService;
		this.genreService = genreService;
		this.exemplaireService = exemplaireService;
	}
	
	@ModelAttribute("jeu")
	public Jeu createJeu() {
		return new Jeu();
	}

	@ModelAttribute("exemplaire")
	public Exemplaire createExemplaire() {
		return new Exemplaire();
	}
	
	@RequestMapping(path={"/", ""})
	public String afficherListeJeux(Model modele){
		
		modele.addAttribute("jeux", jeuService.findAll());
		modele.addAttribute("body", "jeu/liste-jeux");
		modele.addAttribute("title", "Liste des jeux");
		return "index";
	}
	
	@GetMapping(path= {"/ajouter"})
	public String afficherFormulaireAjoutJeu(Model modele) {
		modele.addAttribute("genres", genreService.findAll());
		modele.addAttribute("jeu", new Jeu());
		modele.addAttribute("body", "jeu/formulaire-jeux");
		modele.addAttribute("title", "Ajouter un jeu");
		return "index";
	}

	@PostMapping(path= {"/enregistrer"})
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
			modele.addAttribute("body", "jeu/formulaire-jeux");
			modele.addAttribute("title", "Modifier un jeu");
			return "index";
		}

		return "redirect:/jeux";
	}
	
	@GetMapping(path= {"/{noJeu}"})
	public String afficherFicheJeu(@PathVariable(name="noJeu") int noJeu, Model modele){
		
		Optional<Jeu> jeu = jeuService.findById(noJeu);
		
		if(jeu.isPresent()) {
			modele.addAttribute("jeu", jeu.get());
			modele.addAttribute("exemplaires", exemplaireService.findByJeu(jeu.get()));
			modele.addAttribute("title", jeu.get().getTitre());
		} else {
			modele.addAttribute("jeu", null);
		}

		modele.addAttribute("body", "jeu/fiche-jeu");
		return "index";
	}

	@PostMapping(path= {"/{noJeu}/ajoutExemplaire"})
	public String ajouterExemplaire(@Valid @ModelAttribute("exemplaire") Exemplaire exemplaire, @PathVariable(name="noJeu") int noJeu, BindingResult resultat, Model modele, RedirectAttributes redirectAttr){
		Optional<Jeu> jeuAssocie = jeuService.findById(noJeu);

		if(resultat.hasErrors() || jeuAssocie.isEmpty()) {
			resultat.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			redirectAttr.addFlashAttribute( "org.springframework.validation.BindingResult.exemplaires", resultat);
			redirectAttr.addFlashAttribute("exemplaire", exemplaire);
			return "redirect:/jeux/" + noJeu;
		}

		exemplaire.setJeu(jeuAssocie.get());
		exemplaireService.save(exemplaire);

		return "redirect:/jeux/" + noJeu;
	}
	
	@GetMapping(path= {"/supprimer/{noJeu}"})
	public String suprimerClient(@PathVariable(name="noJeu") int noJeu, Model modele){
		
		jeuService.delete(noJeu);
		return "redirect:/jeux";
	}
	
}
