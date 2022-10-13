package com.chiraranw.housesapp.controller;


import com.chiraranw.housesapp.dto.HouseDto;
import com.chiraranw.housesapp.service.HousesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.CoreMatchers.*;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class HouseControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HousesService housesService;

    @Autowired
    private ObjectMapper objectMapper;

    private HouseDto houseDtoOne;

    private HouseDto houseDtoTwo;

    @BeforeEach
    void setUp() {
        houseDtoOne = HouseDto.builder()
                .url("https://www.anapioficeandfire.com/api/houses/1")
                .name("House Baelish of Harrenhal")
                .region("Region 1")
                .heir("Nation")
                .build();
        houseDtoTwo = HouseDto.builder()
                .url("https://www.anapioficeandfire.com/api/houses/2")
                .name("House Ambrose")
                .region("Region 1")
                .heir("Nation")
                .build();
    }


    @DisplayName("With no parameters given, search houses.")
    @Test
    void searchHousesGivenNoParams() throws Exception {
        Map<String, String> params = new HashMap<>();
        List<HouseDto> houses = Arrays.asList(houseDtoOne, houseDtoTwo);
        given(housesService.search(params)).willReturn(houses);
        ResultActions response = mockMvc.perform(get("/api/v1/houses/search/"));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(houses.size())));
    }

    @DisplayName("With no parameters given, search houses.")
    @Test
    void searchHousesGivenParams() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("name", Collections.singletonList("House Baelish of Harrenhal"));
        params.put("region", Collections.singletonList("Region 1"));
        List<HouseDto> houses = Collections.singletonList(houseDtoOne);
        given(housesService.search(anyMap())).willReturn(houses);
        ResultActions response = mockMvc.perform(
                get("/api/v1/houses/search/").params(params));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(houses.size())))
                .andExpect(jsonPath("$.[0].name", is(houseDtoOne.getName())))
                .andExpect(jsonPath("$.[0].region", is(houseDtoOne.getRegion())));
    }


    @DisplayName("Fetch a house, given an id.")
    @Test
    void searchGivenId() throws Exception {
        Long id=1L;
        given(housesService.searchById(id)).willReturn(houseDtoOne);
        ResultActions response = mockMvc.perform(
                get("/api/v1/houses/"+id));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(houseDtoOne.getName())))
                .andExpect(jsonPath("$.region", is(houseDtoOne.getRegion())));
    }



}
