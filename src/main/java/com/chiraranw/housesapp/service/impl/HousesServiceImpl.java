package com.chiraranw.housesapp.service.impl;

import com.chiraranw.housesapp.dto.HouseDto;
import com.chiraranw.housesapp.exceptions.ResourceNotFoundException;
import com.chiraranw.housesapp.model.House;
import com.chiraranw.housesapp.service.HousesService;
import com.chiraranw.housesapp.util.UIHouses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;


@Slf4j
@Service
public class HousesServiceImpl implements HousesService {

    @Value("${com.chiraranw.api.baseUrl}")
    String baseUrl;
    private final ModelMapper modelMapper;
    private final WebClient client;

    public HousesServiceImpl(ModelMapper modelMapper, WebClient client) {
        this.modelMapper = modelMapper;
        this.client = client;

    }

    @Override
    public List<HouseDto> search(Map<String, String> parameters) {
        List<HouseDto> houses = Objects.requireNonNull(client
                        .get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/houses/")
                                .queryParamIfPresent("name", ofNullable(parameters.get("name")))
                                .queryParamIfPresent("region", ofNullable(parameters.get("region")))
                                .queryParamIfPresent("words", ofNullable(parameters.get("words")))
                                .queryParamIfPresent("hasWords", ofNullable(parameters.get("hasWords")))
                                .queryParamIfPresent("hasTitles", ofNullable(parameters.get("hasTitles")))
                                .queryParamIfPresent("hasSeats", ofNullable(parameters.get("hasSeats")))
                                .queryParamIfPresent("hasDiedOut", ofNullable(parameters.get("hasDiedOut")))
                                .queryParamIfPresent("hasAncestralWeapons", ofNullable(parameters.get("hasAncestralWeapons")))
                                .build())
                        .retrieve()
                        .bodyToFlux(House.class).collect(Collectors.toList())
                        .share()
                        .block())
                .parallelStream()
                .map(house -> modelMapper.map(house, HouseDto.class))
                .collect(Collectors.toList());
        UIHouses.housesSentToUI.clear();
        UIHouses.housesSentToUI.addAll(houses);
        return houses;

    }

    @Override
    public HouseDto searchById(Long id) {
        String url = baseUrl + "houses/" + id;
        return UIHouses
                .housesSentToUI
                .stream()
                .filter(houseDto -> houseDto.getUrl().equals(url))
                .findFirst()
                .orElseGet(() -> client.get()
                        .uri("/houses/{id}", id)
                        .exchangeToMono(
                                clientResponse -> {
                                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                                        throw new ResourceNotFoundException("Not found");
                                    } else {
                                        return clientResponse.bodyToMono(HouseDto.class);
                                    }
                                }
                        )
                        .block());
    }
}
