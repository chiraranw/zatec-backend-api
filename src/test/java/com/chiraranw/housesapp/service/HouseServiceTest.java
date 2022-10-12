package com.chiraranw.housesapp.service;

import com.chiraranw.housesapp.dto.HouseDto;
import com.chiraranw.housesapp.service.impl.HousesServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest
class HouseServiceTest {

    private ClientAndServer mockServer;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private HousesServiceImpl housesService;
    @Autowired
    private ModelMapper modelMapper;
    private HouseDto houseDtoOne;
    private HouseDto houseDtoTwo;


    @BeforeEach
    public void setupMockServer() {
        mockServer = ClientAndServer.startClientAndServer(2001);
        housesService = new HousesServiceImpl(modelMapper, WebClient.builder()
                .baseUrl("http://localhost:" + mockServer.getLocalPort()).build());


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

    @AfterEach
    public void tearDownServer() {
        mockServer.stop();
    }


    @Test
    void fetchMultipleHousesWithNoRequestParameters() throws JsonProcessingException {
        String response = objectMapper.writeValueAsString(Arrays.asList(houseDtoOne, houseDtoTwo));
        Map<String, String> parameters = new HashMap<>();

        mockServer.when(request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/houses/"))
                .respond(response()
                        .withStatusCode(HttpStatus.OK.value())
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(response));

        List<HouseDto> houseDtos = housesService.search(parameters);
        assertEquals(2, houseDtos.size());

        mockServer.verify(request()
                .withMethod(HttpMethod.GET.name())
                .withPath("/houses/"));

    }


    @Test
    void searchItemGivenId() throws JsonProcessingException {
        String response = objectMapper.writeValueAsString(houseDtoOne);
        mockServer.when(request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/houses/1"))
                .respond(response()
                        .withStatusCode(HttpStatus.OK.value())
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(response));

        HouseDto fetchedHouse = housesService.searchById(1L);
        assertEquals(houseDtoOne.getUrl(),fetchedHouse.getUrl());
        mockServer.verify(request()
                .withMethod(HttpMethod.GET.name())
                .withPath("/houses/1"));
    }


}
