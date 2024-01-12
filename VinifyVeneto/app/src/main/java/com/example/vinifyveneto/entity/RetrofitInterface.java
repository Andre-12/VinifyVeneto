package com.example.vinifyveneto.entity;

import java.util.HashMap;
import java.util.List;

import com.example.vinifyveneto.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("/signup")
    Call<ResponseEntity<Void>> executeSignup(@Body HashMap<String, String> map);

    @POST("/loginUser")
    Call<ResponseEntity<User>> executeLoginUser(@Body HashMap<String, String> map);

    @POST("/loginSeller")
    Call<ResponseEntity<Seller>> executeLoginSeller(@Body HashMap<String, String> map);

    @POST("/signupSeller")
    Call<ResponseEntity<Void>> executeSellerSignup(@Body HashMap<String, String> map);

    @POST("/getProducts")
    Call<ResponseEntity<List<Product>>> executeGetProducts(@Body HashMap<String,String> request);

    @POST("/getProduct")
    Call<ResponseEntity<Product>> executeGetProduct(@Body HashMap<String, String> map);

    @POST("/addProduct")
    Call<ResponseEntity<Void>> executeAddProduct(@Body HashMap<String, String> map);

    @POST("/deleteProduct")
    Call<ResponseEntity<Void>> executeDeleteProduct(@Body HashMap<String, String> map);

    @POST("/modifyProduct")
    Call<ResponseEntity<Void>> executeModifyProduct(@Body HashMap<String, String> map);

    @POST("/getUserProducts")
    Call<ResponseEntity<List<Product>>> getUserProducts(@Body HashMap<String, String> map);

    @GET("/getUserProduct/{product}")
    Call<ResponseEntity<Product>> getUserProduct(@Path("product") String product);

    @POST("/addFavorite")
    Call<ResponseEntity<Void>> addFavorite(@Body HashMap<String, String> map);

    @POST("/getFavorites")
    Call<ResponseEntity<List<Product>>> getFavorities(@Body HashMap<String, String> map);

    @POST("/removeFavorite")
    Call<ResponseEntity<Void>> removeFavorite(@Body HashMap<String, String> map);

    @GET("/getSellerInfo/{product}")
    Call<ResponseEntity<Seller>> getSellerInfo(@Path("product") String product);

    @GET("forgotPassword/{id}/{telNum}")
    Call<ResponseEntity<String>> forgotPassword(@Path("id") String id, @Path("telNum") String telNum);

    @POST("/deleteUser")
    Call<ResponseEntity<Void>> deleteUser(@Body HashMap<String, String> map);

    @POST("/deleteSeller")
    Call<ResponseEntity<Void>> deleteSeller(@Body HashMap<String, String> map);

    @POST("/changeSellerPassword")
    Call<ResponseEntity<Void>> changeSellerPassword(@Body HashMap<String, String> map);

    @POST("/getSellerProfile")
    Call<ResponseEntity<Seller>> getSellerProfile(@Body HashMap<String, String> map);
}
