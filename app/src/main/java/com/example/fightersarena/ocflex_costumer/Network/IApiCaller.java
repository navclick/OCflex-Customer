package com.example.fightersarena.ocflex_costumer.Network;

import com.example.fightersarena.ocflex_costumer.Models.CustomerService;
import com.example.fightersarena.ocflex_costumer.Models.CustomerServices;
import com.example.fightersarena.ocflex_costumer.Models.MyOrders;
import com.example.fightersarena.ocflex_costumer.Models.OrderRequest;
import com.example.fightersarena.ocflex_costumer.Models.OrderResponse;
import com.example.fightersarena.ocflex_costumer.Models.Register;
import com.example.fightersarena.ocflex_costumer.Models.RegisterRequest;
import com.example.fightersarena.ocflex_costumer.Models.Token;
import com.example.fightersarena.ocflex_costumer.Models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IApiCaller{
    // Login starts
    @FormUrlEncoded
    @POST(EndPoints.LOGIN)
    Call<Token> GetToken(@Field("username") String username,
                         @Field("password") String password,
                         @Field("grant_type") String grant_type);

    @GET(EndPoints.GETUSER)
    Call<UserResponse> GetUser();

    @POST(EndPoints.REGISTER)
    Call<Register> Register(@Body RegisterRequest registerRequest);

    @POST(EndPoints.ADDORDERS)
    Call<OrderResponse> AddOrders(@Body OrderRequest orderRequest);

    @GET(EndPoints.CUSTOMERSERVICES)
    Call<CustomerServices> GetCustomerServices();

    @GET(EndPoints.GETMYORDERS)
    Call<MyOrders> GetMyOrders();

    @GET(EndPoints.GETACTIVEORDERS)
    Call<MyOrders> GetActiveOrders();

    @GET(EndPoints.GETORDERHISTORY)
    Call<MyOrders> GetOrderHistory();

    // Register starts
//    @FormUrlEncoded
//    @POST(EndPoints.REGISTER)
//    Call<Register> Register(@Field("fullname") String fullname,
//                            @Field("email") String email,
//                            @Field("password") String password,
//                            @Field("confirmpassword") String confirmpassword,
//                            @Field("level") Integer level);
}