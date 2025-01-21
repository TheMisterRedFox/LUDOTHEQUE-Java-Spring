package fr.eni.ludotheque.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping(path="/login")
    public String afficherPageLogin(){

        return "security/login";
    }

}
