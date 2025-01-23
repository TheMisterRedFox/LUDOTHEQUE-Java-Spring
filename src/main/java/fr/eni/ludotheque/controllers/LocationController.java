package fr.eni.ludotheque.controllers;

import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bll.LocationService;
import fr.eni.ludotheque.bo.Location;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final ExemplaireService exemplaireService;
    private LocationService locationService;
    private ClientService clientService;

    public LocationController(LocationService locationService, ExemplaireService exemplaireService, ClientService clientService) {
        this.locationService = locationService;
        this.exemplaireService = exemplaireService;
        this.clientService = clientService;
    }

    @ModelAttribute("dtoLocation")
    public Location createLocation() {
        return new Location();
    }

    @RequestMapping(path={"/", ""})
    public String afficherListeLocations(Model modele){

        modele.addAttribute("locations", locationService.findAll());
        modele.addAttribute("body", "location/liste-locations.html");
        modele.addAttribute("title", "Liste des locations");
        return "index";
    }

    @GetMapping(path= {"/ajouter"})
    public String afficherFormulaireAjoutLocation(Model modele) {
        modele.addAttribute("clients", clientService.findAll());
        modele.addAttribute("exemplaires", exemplaireService.findAll());
        modele.addAttribute("dtoLocation", new DtoLocation());
        modele.addAttribute("body", "location/formulaire-locations");
        modele.addAttribute("title", "Ajouter une location");
        return "index";
    }

    private class DtoLocation {
        private Integer noLocation;

        @NotNull
        private Date dateLocation;

        private Integer idLocataire;

        private List<Integer> idExemplairesLoues;

        public DtoLocation() {
            this.idExemplairesLoues = new ArrayList<>();
        }

        public Integer getNoLocation() {
            return noLocation;
        }

        public void setNoLocation(Integer noLocation) {
            this.noLocation = noLocation;
        }

        public Date getDateLocation() {
            return dateLocation;
        }

        public void setDateLocation(Date dateLocation) {
            this.dateLocation = dateLocation;
        }

        public Integer getIdLocataire() {
            return idLocataire;
        }

        public void setIdLocataire(Integer idLocataire) {
            this.idLocataire = idLocataire;
        }

        public List<Integer> getIdExemplairesLoues() {
            return idExemplairesLoues;
        }

        public void setIdExemplairesLoues(List<Integer> idExemplairesLoues) {
            this.idExemplairesLoues = idExemplairesLoues;
        }
    }
}
