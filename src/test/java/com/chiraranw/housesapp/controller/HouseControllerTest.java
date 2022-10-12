package com.chiraranw.housesapp.controller;


import com.chiraranw.housesapp.dto.HouseDto;
import com.chiraranw.housesapp.service.HousesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class HouseControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HousesService  housesService;

    @Autowired
    private ObjectMapper objectMapper;

    private HouseDto houseDtoOne;

    private HouseDto houseDtoTwo;

    @BeforeEach
    void setUp(){
        houseDtoOne = HouseDto.builder()
                .url("https://www.anapioficeandfire.com/api/houses/1")
                .name("House Baelish of Harrenhal")
                .region("Region 1")
                .heir("Nation")
                .build();
        houseDtoTwo = HouseDto.builder()
                .url("https://www.anapioficeandfire.com/api/houses/2")
                .name("House Baelish of Harrenhal")
                .region("Region 1")
                .heir("Nation")
                .build();
    }







}
