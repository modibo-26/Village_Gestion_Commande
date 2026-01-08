package com.GestionCommande.Service;

import com.GestionCommande.Entity.Client;
import com.GestionCommande.Entity.Commande;
import com.GestionCommande.Repository.ClientRepository;
import com.GestionCommande.Repository.CommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService implements ICommandeService {

    final CommandeRepository repository;

    final ClientRepository clientRepository;

    public CommandeService(CommandeRepository repository, ClientRepository clientRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Commande ajouterCommande(Commande commande) {
        repository.save(commande);
        return commande;
    }

    @Override
    public List<Commande> listerCommande() {
        return repository.findAll();
    }

    @Override
    public Commande recupererCommande(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvé"));
    }

    @Override
    public Commande associerCommande(long idCommande, String nomClient) {
        Commande commande = repository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvé"));
        Client client = clientRepository.findByNom(nomClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        commande.setClient(client);
        return repository.save(commande);
    }
}
