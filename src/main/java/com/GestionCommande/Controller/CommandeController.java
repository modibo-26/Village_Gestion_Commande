package com.GestionCommande.Controller;

import com.GestionCommande.Entity.Commande;
import com.GestionCommande.Service.CommandeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commande")
public class CommandeController {

    private final CommandeService service;

    public CommandeController(CommandeService service) {
        this.service = service;
    }

    @PostMapping
    public Commande ajouterCommande(@Valid @RequestBody Commande commande) {
        service.ajouterCommande(commande);
        return commande;
    }

    @GetMapping
    public List<Commande> listerCommande() {
        return service.listerCommande();
    }

    @GetMapping("/{id}")
    public Commande recupererCommande(@PathVariable long id) {
        return service.recupererCommande(id);
    }

    @PutMapping("/{id}/client")
    public Commande associerCommande(@PathVariable long id, @RequestBody String nomClient) {
        return service.associerCommande(id, nomClient);
    }
}
