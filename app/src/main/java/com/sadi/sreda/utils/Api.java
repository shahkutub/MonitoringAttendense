package com.sadi.sreda.utils;


import com.sadi.sreda.model.LoinResponse;
import com.sadi.sreda.model.MyRecordsInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Sadi on 11/18/2017.
 */

public interface Api {

    //String BASE_URL = "http://offerian.com/";
    //String BASE_URL = "http://192.168.0.119/renewableenergy/api/";
    String BASE_URL = "http://css-bd.com/attendance-system/";


    @FormUrlEncoded
    @POST("login")
    Call<LoinResponse> getLoginUser(
            @Field("email") String email,
            @Field("password") String password

    );


    @GET("api")
    Call<List<MyRecordsInfo>> getRecords(

    );


//    @GET("api/get-occupation")
//    Call<List<ScRelation>> getOccupation(
//
//    );

//    @GET("fuel_generation_report")
//    Call<Info_FuelGenResponse> getFuel(
//            @Query("email") String email,
//            @Query("password") String password
//
//    );
//
//
//    @GET("get_technology_names")
//    Call<Info_GetTechnologyNames> getTechnologyName(
//            @Query("email") String email,
//            @Query("password") String password
//
//    );
//
//    @GET("technology_wise_generation_report")
//    Call<Info_TechWiseGenReportResponse> getTechnologyNameReport(
//            @Query("email") String email,
//            @Query("password") String password
//
//    );


}
