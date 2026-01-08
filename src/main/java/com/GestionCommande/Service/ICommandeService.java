package com.GestionCommande.Service;

import com.GestionCommande.Entity.Commande;

import java.util.List;

public interface ICommandeService {

    Commande ajouterCommande(Commande commande);

    List<Commande> listerCommande();

    Commande recupererCommande(long id);

    Commande associerCommande(long idClient, String nom);
}
