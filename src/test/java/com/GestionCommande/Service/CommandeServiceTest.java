package com.GestionCommande.Service;

import com.GestionCommande.Entity.Client;
import com.GestionCommande.Entity.Commande;
import com.GestionCommande.Repository.ClientRepository;
import com.GestionCommande.Repository.CommandeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandeServiceTest {

    @Mock
    private CommandeRepository repository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private CommandeService service;

    private Commande commande;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client("Traore", "traore@email.com");
        client.setId(1L);

        commande = new Commande("Laptop", 2);
        commande.setId(1L);
    }

    @Test
    void ajouterCommande_Success() {
        when(repository.save(commande)).thenReturn(commande);

        Commande result = service.ajouterCommande(commande);

        assertEquals("Laptop", result.getProduit());
        verify(repository, times(1)).save(commande);
    }

    @Test
    void listerCommande_Success() {
        List<Commande> commandes = Arrays.asList(commande);
        when(repository.findAll()).thenReturn(commandes);

        List<Commande> result = service.listerCommande();

        assertEquals(1, result.size());
    }

    @Test
    void recupererCommande_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(commande));

        Commande result = service.recupererCommande(1L);

        assertEquals("Laptop", result.getProduit());
    }

    @Test
    void recupererCommande_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.recupererCommande(99L);
        });
    }

    @Test
    void associerCommande_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(commande));
        when(clientRepository.findByNom("Traore")).thenReturn(Optional.of(client));
        when(repository.save(commande)).thenReturn(commande);

        Commande result = service.associerCommande(1L, "Traore");

        assertEquals(client, result.getClient());
        verify(repository, times(1)).save(commande);
    }

    @Test
    void associerCommande_ClientNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(commande));
        when(clientRepository.findByNom("Inconnu")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.associerCommande(1L, "Inconnu");
        });
    }
}