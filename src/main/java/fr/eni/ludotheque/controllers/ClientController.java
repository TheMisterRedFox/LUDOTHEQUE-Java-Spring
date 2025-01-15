package fr.eni.ludotheque.controllers;

import java.util.List;
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
		return "client/liste-clients";
	}
	
	@GetMapping(path= {"/ajouter"})
	public String afficherFormulaireAjoutClient(){
		
		return "client/formulaire-client";
	}
	
	@GetMapping(path= {"/{noClient}"})
	public String afficherFicheClient(@PathVariable(name="noClient") int noClient, Model modele){
		
		Optional<Client> client = clientService.findById(noClient);
		
		if(client.isPresent()) {
			modele.addAttribute("client", client.get());
		} else {
			modele.addAttribute("client", null);
		}
		
		
		return "client/fiche-client";
	}
	
	@GetMapping("/modifier/{noClient}")
    public String getModifierClient(@PathVariable(name="noClient") int noClient, Model model) {
        Optional<Client> clientOpt = clientService.findById(noClient);
        
        if (clientOpt.isPresent()) {
            model.addAttribute("client", clientOpt.get());
            return "client/formulaire-client";
        } 
        
        return "client/liste-clients";
    }
	
	@GetMapping(path= {"/supprimer/{noClient}"})
	public String suprimerClient(@PathVariable(name="noClient") int noClient, Model modele){
		
		clientService.delete(noClient);
		return "redirect:/clients";
	}
	
	@PostMapping(path= {"/ajouter"})
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
