package com.GestionCommande.Controller;

import com.GestionCommande.Entity.Commande;
import com.GestionCommande.Service.CommandeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CommandeController.class)
public class CommandeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommandeService service;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Commande commande;

    @BeforeEach
    void setUp() {
        commande = new Commande("Laptop", 2);
        commande.setId(1L);
    }

    @Test
    void ajouterCommande_Success() throws Exception {
        when(service.ajouterCommande(any(Commande.class))).thenReturn(commande);

        mockMvc.perform(post("/commande")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commande)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produit").value("Laptop"))
                .andExpect(jsonPath("$.quantite").value(2));
    }

    @Test
    void listerCommande_Success() throws Exception {
        List<Commande> commandes = Arrays.asList(commande);
        when(service.listerCommande()).thenReturn(commandes);

        mockMvc.perform(get("/commande"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].produit").value("Laptop"));
    }

    @Test
    void recupererCommande_Success() throws Exception {
        when(service.recupererCommande(1L)).thenReturn(commande);

        mockMvc.perform(get("/commande/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produit").value("Laptop"));
    }

    @Test
    void associerCommande_Success() throws Exception {
        when(service.associerCommande(eq(1L), eq("Traore"))).thenReturn(commande);

        mockMvc.perform(put("/commande/1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Traore"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produit").value("Laptop"));
    }

    @Test
    void ajouterCommande_ValidationError() throws Exception {
        Commande invalidCommande = new Commande("", 0);

        mockMvc.perform(post("/commande")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCommande)))
                .andExpect(status().isBadRequest());
    }

}
