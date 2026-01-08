package com.GestionCommande.Service;

import com.GestionCommande.Entity.Client;
import com.GestionCommande.Entity.Commande;

import java.util.List;

public interface IClientService {

    Client ajouterClient(Client client);

    List<Client> listerCient();

    Client recupererClient(long id);

    List<Commande> getAllCommandes(long id);

}
