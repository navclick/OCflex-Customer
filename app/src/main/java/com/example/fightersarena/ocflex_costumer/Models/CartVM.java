package com.example.fightersarena.ocflex_costumer.Models;

public class CartVM{

    public CartVM(){

    }

    public CartVM(int id, String servicename, String date, int total){
        this.id = id;
        this.serviceName = servicename;
        this.date = date;
        this.total = total;
    }

    private String serviceName, date;
    private Integer id, total;

    public int getId(){ return id; }

    public void setId(int id){ this.id = id; }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String name) {
        this.serviceName = name;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}