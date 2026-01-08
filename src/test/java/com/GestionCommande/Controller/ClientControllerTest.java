package com.GestionCommande.Controller;

import com.GestionCommande.Entity.Client;
import com.GestionCommande.Service.ClientService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService service;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client("Traore", "traore@email.com");
        client.setId(1L);
    }

    @Test
    void ajouterClient_Success() throws Exception {
        when(service.ajouterClient(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Traore"))
                .andExpect(jsonPath("$.email").value("traore@email.com"));
    }

    @Test
    void listerClient_Success() throws Exception {
        List<Client> clients = Arrays.asList(client);
        when(service.listerCient()).thenReturn(clients);

        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nom").value("Traore"));
    }

    @Test
    void recupererClient_Success() throws Exception {
        when(service.recupererClient(1L)).thenReturn(client);

        mockMvc.perform(get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Traore"));
    }

    @Test
    void ajouterClient_ValidationError() throws Exception {
        Client invalidClient = new Client("", "pas-un-email");

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest());
    }
}