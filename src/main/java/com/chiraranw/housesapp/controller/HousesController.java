package com.chiraranw.housesapp.controller;

import com.chiraranw.housesapp.dto.HouseDto;
import com.chiraranw.housesapp.service.HousesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/houses")
public class HousesController {

    private final HousesService housesService;

    public HousesController(HousesService housesService) {
        this.housesService = housesService;
    }

    @GetMapping("/search/")
    public ResponseEntity<List<HouseDto>> search(@RequestParam Map<String, String> params) {
        List<HouseDto> searchResults = housesService.search(params);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<HouseDto> searchById(@PathVariable("id") Long id) {
        HouseDto houseDto = housesService.searchById(id);
        return new ResponseEntity<>(houseDto, HttpStatus.OK);
    }
}
