package com.example.fightersarena.ocflex_costumer.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerService {

    public CustomerService(){

    }

    public CustomerService(int id, String name, Integer rates, String imageUrl){
        this.id = id;
        this.name = name;
        this.rates = rates;
        this.imageUrl = name;
    }

    private String name,imageUrl;
    private Integer id, rates;

    public int getId(){ return id; }

    public void setId(int id){ this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRates() {
        return rates;
    }

    public void setRates(Integer rates) {
        this.rates = rates;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}