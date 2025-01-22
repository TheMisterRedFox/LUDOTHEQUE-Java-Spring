package fr.eni.ludotheque.controllers;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bo.Client;
import jakarta.validation.Valid;

@RequestMapping("/clients")
@Controller
public class ClientController {
	
	private ClientService clientService;
		
	public ClientController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}
	
	@ModelAttribute("client")
	public Client createClient() {
		return new Client();
	}

	@RequestMapping(path={"/", ""})
	public String afficherListeClients(Model modele){
		
		modele.addAttribute("clients", clientService.findAll());
		modele.addAttribute("body", "client/liste-clients");
		modele.addAttribute("title", "Liste des clients");
		return "index";
	}
	
	@GetMapping(path= {"/ajouter"})
	public String afficherFormulaireAjoutClient(Model modele){

		modele.addAttribute("title", "Ajouter un client");
		modele.addAttribute("body", "client/formulaire-client");
		return "index";
	}
	
	@GetMapping(path= {"/{noClient}"})
	public String afficherFicheClient(@PathVariable(name="noClient") int noClient, Model modele){
		
		Optional<Client> client = clientService.findById(noClient);
		String htmlTitle = "Fiche client";
		
		if(client.isPresent()) {
			modele.addAttribute("client", client.get());
			htmlTitle = client.get().getNom() + " " + client.get().getPrenom();
		} else {
			modele.addAttribute("client", null);
		}

		modele.addAttribute("title", htmlTitle);
		modele.addAttribute("body", "client/fiche-client");
		
		return "index";
	}
	
	@GetMapping("/modifier/{noClient}")
    public String getModifierClient(@PathVariable(name="noClient") int noClient, Model modele) {
        Optional<Client> clientOpt = clientService.findById(noClient);
		String htmlBody = "";

        if (clientOpt.isPresent()) {
            modele.addAttribute("client", clientOpt.get());
            htmlBody = "client/formulaire-client";
        } else {
			htmlBody = "client/liste-clients";
		}

		modele.addAttribute("title", "Modifier le client");
		modele.addAttribute("body", "client/formulaire-client");

        return "index";
    }
	
	@GetMapping(path= {"/supprimer/{noClient}"})
	public String suprimerClient(@PathVariable(name="noClient") int noClient, Model modele){
		
		clientService.delete(noClient);
		return "redirect:/clients";
	}
	
	@PostMapping(path= {"/enregistrer"})
	public String ajouterClient(@Valid @ModelAttribute("client") Client client, BindingResult resultat, Model modele, RedirectAttributes redirectAttr){

		if(resultat.hasErrors()) {
			
			redirectAttr.addFlashAttribute( "org.springframework.validation.BindingResult.client", resultat);
			redirectAttr.addFlashAttribute("client", client);
			return "redirect:/clients/ajouter";
		}
		clientService.save(client);
		return "redirect:/clients";
	}
}
