package com.GestionCommande.Service;

import com.GestionCommande.Entity.Client;
import com.GestionCommande.Repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {

    final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client ajouterClient(Client client) {
        repository.save(client);
        return client;
    }

    @Override
    public List<Client> listerCient() {
        return repository.findAll();
    }

    @Override
    public Client recupererClient(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
    }
}
