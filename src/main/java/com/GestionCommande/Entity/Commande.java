package com.GestionCommande.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;



    @Entity
    public class Commande {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @NotBlank(message = "Le produit est obligatoire")
        private String produit;

        @Min(value = 1, message = "La quantité doit être supérieure à 0")
        private int quantite;

        @ManyToOne
        @JoinColumn(name = "client_id")
        @JsonBackReference
        private Client client;

        public Commande() {
        }

        public Commande(String produit, int quantite) {
            this.produit = produit;
            this.quantite = quantite;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getProduit() {
            return produit;
        }

        public void setProduit(String produit) {
            this.produit = produit;
        }

        public int getQuantite() {
            return quantite;
        }

        public void setQuantite(int quantite) {
            this.quantite = quantite;
        }

        public Client getClient() {
            return client;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public Long getClientId() {
            return client != null ? client.getId() : null;
        }
    }
