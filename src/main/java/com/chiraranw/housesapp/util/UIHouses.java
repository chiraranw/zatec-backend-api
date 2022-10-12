package com.chiraranw.housesapp.util;

import com.chiraranw.housesapp.dto.HouseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UIHouses {
    public static List<HouseDto> housesSentToUI= new ArrayList<>();
}
