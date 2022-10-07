package com.chiraranw.housesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseDto {
    public String url;
    public String name;
    public String region;
    public String coatOfArms;
    public String words;
    public List<String> titles;
    public List<String> seats;
    public String currentLord;
    public String heir;
    public String overlord;
    public String founded;
    public String founder;
    public String diedOut;
    public List<String> ancestralWeapons;
    public List<String> cadetBranches;
    public List<String> swornMembers;
}