package com.sadi.sreda.utils;


import com.sadi.sreda.model.ExAttanRecordsInfo;
import com.sadi.sreda.model.LocationInfo;
import com.sadi.sreda.model.LoinResponse;
import com.sadi.sreda.model.MyRecordsInfo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by Sadi on 11/18/2017.
 */

public interface Api {

    //String BASE_URL = "http://offerian.com/";
    String BASE_URL_login = "http://css-bd.com/attendance-system/";
    String BASE_URL_attten = "http://css-bd.com/attendance-system/api/userAttendance/";
    String BASE_URL_attan_ex = "http://css-bd.com/attendance-system/api/userApproveAttendance/";
    String BASE_URL = "http://css-bd.com/attendance-system/api/";



    @Headers("Content-Type: application/json")
    @POST("api")
    Call<LoinResponse> getUser(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("checkInStore")
    Call<LoinResponse> storeCheckIn(@Body String body);


    @Headers("Content-Type: application/json")
    @POST("checkOutStore")
    Call<LoinResponse> storeCheckOut(@Body String body);

    @Headers("Content-Type: application/json")
        @POST("checkException")
        Call<LoinResponse> storeException(@Body String body);

    @FormUrlEncoded
    @POST("api")
    Call<LoinResponse> getLoginUser(
            @Field("email") String email,
            @Field("password") String password

    );



    @Headers("Content-Type: application/json")
    @POST("changePassword")
    Call<LoinResponse> changePass(@Body String body);






    @GET("userAttendance")
    Call<List<MyRecordsInfo>> getRecords(


    );

@GET("officeLocation")
    Call<List<LocationInfo>> getofficeLocation(


    );

    @GET("")
    Call<List<MyRecordsInfo>> getAllRecords(@Url String id);

 @GET("")
    Call<List<ExAttanRecordsInfo>> getAllRecordsEx(@Url String id);

    @FormUrlEncoded
    @POST("uploadPhoto")
    Call<LoinResponse> postUserImage(
            @Field("user_id") String user_id,
            @Field("images") String images

    );

    @Multipart
    @POST("uploadPhoto")
    Call<LoinResponse> postImage(@Part MultipartBody.Part images, @Part("user_id") String user_id);

    @Multipart
    @POST("uploadPhoto")
    Call<LoinResponse> uploadImage(@Part("images") RequestBody file, @Part("user_id") RequestBody desc);


//    @GET
//    Call<List<MyRecordsInfo>> getAllRecords();

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
