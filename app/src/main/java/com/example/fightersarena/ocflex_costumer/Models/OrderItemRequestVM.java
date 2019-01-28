package com.example.fightersarena.ocflex_costumer.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItemRequestVM {

    @SerializedName("ServiceId")
    @Expose
    private Integer serviceId;
    @SerializedName("Hours")
    @Expose
    private Integer hours;
    @SerializedName("Rates")
    @Expose
    private Integer rates;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("StartTime")
    @Expose
    private String startTime;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getRates() {
        return rates;
    }

    public void setRates(Integer rates) {
        this.rates = rates;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}