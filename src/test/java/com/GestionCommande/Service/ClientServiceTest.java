package com.GestionCommande.Service;


import com.GestionCommande.Entity.Client;
import com.GestionCommande.Repository.ClientRepository;
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
public class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientService service;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client("Traore", "traore@email.com");
        client.setId(1L);
    }

    @Test
    void ajouterClient_Success() {
        when(repository.save(client)).thenReturn(client);

        Client result = service.ajouterClient(client);

        assertEquals("Traore", result.getNom());
        verify(repository, times(1)).save(client);
    }

    @Test
    void listerClient_Success() {
        List<Client> clients = Arrays.asList(client);
        when(repository.findAll()).thenReturn(clients);

        List<Client> result = service.listerCient();

        assertEquals(1, result.size());
    }

    @Test
    void recupererClient_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(client));

        Client result = service.recupererClient(1L);

        assertEquals("Traore", result.getNom());
    }

    @Test
    void recupererClient_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.recupererClient(99L);
        });
    }
}