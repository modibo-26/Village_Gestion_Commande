package com.GestionCommande.Controller;

import com.GestionCommande.Entity.Client;
import com.GestionCommande.Entity.Commande;
import com.GestionCommande.Service.ClientService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Client ajouterClient(@Valid @RequestBody Client client) {
        service.ajouterClient(client);
        return client;
    }

    @GetMapping
    public List<Client> listerClient() {
        return service.listerCient();
    }

    @GetMapping("/{id}")
    public Client recupererClient(@PathVariable long id) {
        return service.recupererClient(id);
    }

    @GetMapping("/{id}/commande")
    public List<Commande> getAllCommandes(@PathVariable long id) {
        return service.getAllCommandes(id);
    }

    @GetMapping("/debug")
    public String debug() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return "User: " + auth.getName() + " | Roles: " + auth.getAuthorities();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable long id) {
        service.deleteUser(id);
    }

}
