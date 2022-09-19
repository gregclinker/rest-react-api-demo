package com.ps.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class ControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/cards")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]]").exists())
                .andExpect(jsonPath("$.[0]").isNotEmpty())
                .andExpect(jsonPath("$.[0].name").value("Fred Flintsone"))
                .andExpect(jsonPath("$.[0].number").value("4003600006848770"))
                .andExpect(jsonPath("$.[0].creditLimit").value("100.00"))
                .andExpect(jsonPath("$.[1].name").value("Wilma Flintsone"))
                .andExpect(jsonPath("$.[1].number").value("4003600006849034"))
                .andExpect(jsonPath("$.[1].creditLimit").value("200.00"));
    }

    @Test
    void getOne() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/cards/101")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Fred Flintsone"))
                .andExpect(jsonPath("$.number").value("4003600006848770"))
                .andExpect(jsonPath("$.creditLimit").value("100.00"));
    }

    @Test
    void cardNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/cards/999")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.name").value("CardNotFoundException"))
                .andExpect(jsonPath("$.message").value("card not found for id 999"));
    }

    @Test
    void create() throws Exception {
        final Card testCard = new Card("test", VALID_CARD_NUMBERS.get(0), 125.00);
        mvc.perform(MockMvcRequestBuilders
                .post("/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testCard)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(testCard.getName()))
                .andExpect(jsonPath("$.number").value(testCard.getNumber()))
                .andExpect(jsonPath("$.creditLimit").value(testCard.getCreditLimit().doubleValue()));
    }

    @Test
    void invalidCardNumber() throws Exception {
        final Card testCard = new Card("test", IN_VALID_CARD_NUMBERS.get(0), 125.00);
        mvc.perform(MockMvcRequestBuilders
                .post("/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testCard)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("InvalidCardNumberException"))
                .andExpect(jsonPath("$.message").value(testCard.getNumber()));
    }
}