package de.neuefische.springexceptionhandlingtask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalControllerTest {
    private static MockWebServer mockWebServer;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void init() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getAnimalSpecies() throws Exception {
        String animal = "dog";
        MockResponse response = new MockResponse();
        response.setBody(animal);
        response.setHeader("Content-Type", "application/json");

        mockWebServer.enqueue(response);

        mockMvc.perform(get("/api/animals/dog"))
                .andExpect(status().isOk())
                .andExpect(content().string(animal));
    }

    @Test
    void getAnimalSpeciesWrong() throws Exception {
        String animal = "cat";
        MockResponse response = new MockResponse();
        response.setBody(animal);
        response.setHeader("Content-Type", "application/json");

        mockWebServer.enqueue(response);

        mockMvc.perform(get("/api/animals/cat"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    void getCarBrand() throws Exception {
        String car = "porsche";
        MockResponse response = new MockResponse();
        response.setBody(car);
        response.setHeader("Content-Type", "application/json");

        mockWebServer.enqueue(response);

        mockMvc.perform(get("/api/cars/porsche"))
                .andExpect(status().isOk())
                .andExpect(content().string(car));
    }

    @Test
    void getCarBrandWrong() throws Exception {
        String car = "vw";
        MockResponse response = new MockResponse();
        response.setBody(car);
        response.setHeader("Content-Type", "application/json");

        mockWebServer.enqueue(response);

        mockMvc.perform(get("/api/cars/vw"))
                .andExpect(status().isNotAcceptable());
    }
}
