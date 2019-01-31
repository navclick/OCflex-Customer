package com.example.fightersarena.ocflex_costumer.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrder {

    public MyOrder(){

    }

    public MyOrder(int id, String servicename, int total, String date){
        this.id = id;
        this.serviceName = servicename;
        this.rates = total;
        this.startDate = date;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("serviceId")
    @Expose
    private Integer serviceId;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("hours")
    @Expose
    private Integer hours;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("additionalHours")
    @Expose
    private Integer additionalHours;
    @SerializedName("rates")
    @Expose
    private Integer rates;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("startTime")
    @Expose
    private Object startTime;
    @SerializedName("assignedTo")
    @Expose
    private Object assignedTo;
    @SerializedName("addedOn")
    @Expose
    private String addedOn;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAdditionalHours() {
        return additionalHours;
    }

    public void setAdditionalHours(Integer additionalHours) {
        this.additionalHours = additionalHours;
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

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    public Object getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Object assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}