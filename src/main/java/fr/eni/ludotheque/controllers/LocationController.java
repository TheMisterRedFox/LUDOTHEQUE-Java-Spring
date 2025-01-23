package fr.eni.ludotheque.controllers;

import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bll.LocationService;
import fr.eni.ludotheque.bo.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public DtoLocation createLocation() {
        return new DtoLocation();
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
        modele.addAttribute("exemplaires", exemplaireService.findAllLouable());
        modele.addAttribute("dtoLocation", new DtoLocation());
        modele.addAttribute("body", "location/formulaire-locations");
        modele.addAttribute("title", "Ajouter une location");
        return "index";
    }

    @PostMapping(path= {"/enregistrer"})
    public String ajouterLocation(@Valid @ModelAttribute("dtoLocation") DtoLocation dtoLocation, BindingResult resultat, Model modele, RedirectAttributes redirectAttr) throws ParseException {
        if(resultat.hasErrors()) {

            resultat.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            redirectAttr.addFlashAttribute( "org.springframework.validation.BindingResult.dtoLocation", resultat);
            redirectAttr.addFlashAttribute("dtoLocation", dtoLocation);
            return "redirect:/locations/ajouter";
        }

        Location newLocation = new Location();
        newLocation.setDateLocation(new SimpleDateFormat("yyyy-MM-dd").parse(dtoLocation.getDateLocation()));
        newLocation.setPaye(false);

        List<DetailLocation> listeDetailLocation = new ArrayList<DetailLocation>();
        dtoLocation.idExemplairesLoues.forEach(detailId -> {
            Optional<Exemplaire> optExemplaire = exemplaireService.findById(detailId);
            if(optExemplaire.isPresent()) {
                DetailLocation detailLocation = new DetailLocation();
                detailLocation.setExemplaireLoue(optExemplaire.get());
                detailLocation.setTarifLocation(optExemplaire.get().getJeu().getTarifJournee());
                detailLocation.setLocation(newLocation);
                listeDetailLocation.add(detailLocation);
            }
        });
        newLocation.setLignesLocation(listeDetailLocation);

        Optional<Client> optLocataire = clientService.findById(dtoLocation.getIdLocataire());
        if(optLocataire.isPresent()) {
            newLocation.setLocataire(optLocataire.get());
            locationService.save(newLocation);
        }

        return "redirect:/locations";
    }

    public class DtoLocation {
        private Integer noLocation;

        @NotNull
        private String dateLocation;

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

        public String getDateLocation() {
            return dateLocation;
        }

        public void setDateLocation(String dateLocation) {
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
