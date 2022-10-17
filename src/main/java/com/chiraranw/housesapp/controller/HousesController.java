package com.chiraranw.housesapp.controller;

import com.chiraranw.housesapp.dto.HouseDto;
import com.chiraranw.housesapp.service.HousesService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/houses")
@Tag(name = "Zatec Houses API")
public class HousesController {

    private final HousesService housesService;

    public HousesController(HousesService housesService) {
        this.housesService = housesService;
    }


    @ApiOperation(value = "Get houses by given parameters.", notes = "Returns a list of houses.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The houses were not found")
    })
    @GetMapping("/search/")
    public ResponseEntity<List<HouseDto>> search(@RequestParam Map<String, String> params) {
        List<HouseDto> searchResults = housesService.search(params);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @ApiOperation(value = "Get houses by  id.", notes = "Returns a house.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The houses were not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HouseDto> searchById(@PathVariable("id") Long id) {
        HouseDto houseDto = housesService.searchById(id);
        return new ResponseEntity<>(houseDto, HttpStatus.OK);
    }
}
