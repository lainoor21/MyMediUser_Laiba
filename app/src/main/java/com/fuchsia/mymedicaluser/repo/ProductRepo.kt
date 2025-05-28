package com.fuchsia.mymedicaluser.repo

import com.fuchsia.mymedicaluser.api.ApiBuilder
import com.fuchsia.mymedicaluser.common.Results
import com.fuchsia.mymedicaluser.model.CreateOrderResponse
import com.fuchsia.mymedicaluser.model.GetAllOrdersResponse
import com.fuchsia.mymedicaluser.model.ProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Field
import javax.inject.Inject

class ProductRepo @Inject constructor(private val apiBuilder: ApiBuilder){

    suspend fun getAllProducts(): Flow<Results<Response<ProductResponse>>> = flow {

        emit(Results.Loading)
        try {
            val response = apiBuilder.api.getAllProducts()
            emit(Results.Success(response))

        }catch ( e: Exception){
            emit(Results.Error(e.message.toString()))
        }

    }

    suspend fun createOrder(
        user_name: String,
        quantity: String,
        product_price: String,
        total_price: String,
        product_name: String,
        message: String,
        category: String,
        user_id: String,
        email: String,
        address: String,
        phone_number: String,
        product_id: String
    ): Flow<Results<Response<CreateOrderResponse>>> = flow {
        emit(Results.Loading)
        try {
            val response = apiBuilder.api.createOrderDetails(
                user_name = user_name,
                quantity = quantity,
                product_price = product_price,
                total_price = total_price,
                product_name = product_name,
                message = message,
                category = category,
                user_id1 = user_id,
                email = email,
                address = address,
                phone_number = phone_number,
                product_id = product_id

            )
            emit(Results.Success(response))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }



    suspend fun get_AllOrdersHistory(): Flow<Results<Response<GetAllOrdersResponse>>> = flow {

        emit(Results.Loading)
        try {
            val response = apiBuilder.api.getAllOrderDetails()
            emit(Results.Success(response))

        }catch ( e: Exception){
            emit(Results.Error(e.message.toString()))
        }

    }
}