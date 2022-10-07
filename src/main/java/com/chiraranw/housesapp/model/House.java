package com.chiraranw.housesapp.model;

import lombok.Data;

import java.util.List;

@Data
public class House {
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