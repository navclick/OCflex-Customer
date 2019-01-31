package com.example.fightersarena.ocflex_costumer.Network;

public class EndPoints {
    public static final String API_PREFIX = "api/";

    public static final String LOGIN = "oauth/token";

    public static final String REGISTER = API_PREFIX + "accounts/create";
    public static final String GETUSER = API_PREFIX + "accounts/getuser";
    public static final String GETMYORDERS = API_PREFIX + "order/getmyorders";
    public static final String GETACTIVEORDERS = API_PREFIX + "order/getactiveorders";
    public static final String GETORDERHISTORY = API_PREFIX + "order/getorderhistory";
    public static final String ADDORDERS = API_PREFIX + "order/addorders";
    public static final String CUSTOMERSERVICES = API_PREFIX + "customerservice/getcustomerservices";
}