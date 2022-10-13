package com.chiraranw.housesapp.integration.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class HousesApiIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("Find House given existing id.")
    @Test
    void findHouseByExistingId() throws Exception {
        Long id=1L;
        mockMvc
                .perform(get("/api/v1/houses/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("House Algood")))
                .andExpect(jsonPath("$.region", is("The Westerlands")))
                .andExpect(jsonPath("$.url", is("https://www.anapioficeandfire.com/api/houses/1")));
    }

    @DisplayName("Find Houses given no params.")
    @Test
    void findHouseGivenNoParams() throws Exception {
        mockMvc
                .perform(get("/api/v1/houses/search/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @DisplayName("Find Houses given  params.")
    @Test
    void findHouseGivenCorrectParams() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("name", Collections.singletonList("House Baelish of Harrenhal"));
        params.put("region", Collections.singletonList("The Riverlands"));
        mockMvc
                .perform(get("/api/v1/houses/search/")
                        .params(params)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }



    @DisplayName("Find House given no params.")
    @Test
    void findHousesGivenNoParams() throws Exception {
        long id=1000000L;
        mockMvc
                .perform(get("/api/v1/houses/"+id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
