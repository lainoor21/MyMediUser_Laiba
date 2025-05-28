package com.fuchsia.mymedicaluser.api

import com.fuchsia.mymedicaluser.model.CreateOrderResponse
import com.fuchsia.mymedicaluser.model.CreateUserModel
import com.fuchsia.mymedicaluser.model.GetAllOrdersResponse
import com.fuchsia.mymedicaluser.model.GetUserModel
import com.fuchsia.mymedicaluser.model.ProductResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("createUser")
    suspend fun createUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("address") address: String,
        @Field("pincode") pincode: String,
        @Field("phone_number") phone_number: String


    ): Response<CreateUserModel>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("user_id") userId: String
    ): Response<GetUserModel>

    @FormUrlEncoded
    @POST("logIn")
    suspend fun logIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<GetUserModel>


    @FormUrlEncoded
    @PATCH("updateUser")
    suspend fun updateUserDetails(
        @Field("user_id") user_id: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("address") address: String,
        @Field("phone_number") phone_number: String,
        @Field("pincode") pincode: String,
    ): Response<GetUserModel>

    @GET("getAllProducts")
    suspend fun getAllProducts(): Response<ProductResponse>


    @FormUrlEncoded
    @POST("createOrderDetails")
    suspend fun createOrderDetails(
        @Field("user_name") user_name: String,
        @Field("quantity") quantity: String,
        @Field("product_price") product_price: String,
        @Field("total_price") total_price: String,
        @Field("product_name") product_name: String,
        @Field("product_id") product_id: String,
        @Field("message") message: String,
        @Field("category") category: String,
        @Field("user_id") user_id1: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("phone_number") phone_number: String

    ): Response<CreateOrderResponse>

    @GET("getAllOrderDetails")
    suspend fun getAllOrderDetails(): Response<GetAllOrdersResponse>

}