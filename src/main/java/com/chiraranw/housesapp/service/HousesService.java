package com.chiraranw.housesapp.service;

import com.chiraranw.housesapp.dto.HouseDto;

import java.util.List;
import java.util.Map;

public interface HousesService {

 List<HouseDto> search(Map<String, String > parameters);

 HouseDto searchById(Long id);
}
