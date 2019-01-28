package com.example.fightersarena.ocflex_costumer.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequest {

    @SerializedName("PromoCode")
    @Expose
    private String promoCode;
    @SerializedName("OrderAddress")
    @Expose
    private String orderAddress;
    @SerializedName("OrderCity")
    @Expose
    private String orderCity;
    @SerializedName("OrderPostal")
    @Expose
    private String orderPostal;
    @SerializedName("OrderPhone")
    @Expose
    private String orderPhone;
    @SerializedName("OrderItemRequestVM")
    @Expose
    private List<OrderItemRequestVM> orderItemRequestVM = null;

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderCity() {
        return orderCity;
    }

    public void setOrderCity(String orderCity) {
        this.orderCity = orderCity;
    }

    public String getOrderPostal() {
        return orderPostal;
    }

    public void setOrderPostal(String orderPostal) {
        this.orderPostal = orderPostal;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public List<OrderItemRequestVM> getOrderItemRequestVM() {
        return orderItemRequestVM;
    }

    public void setOrderItemRequestVM(List<OrderItemRequestVM> orderItemRequestVM) {
        this.orderItemRequestVM = orderItemRequestVM;
    }
}