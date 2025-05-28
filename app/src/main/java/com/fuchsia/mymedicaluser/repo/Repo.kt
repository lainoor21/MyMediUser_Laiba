package com.fuchsia.mymedicaluser.repo

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.fuchsia.mymedicaluser.api.ApiBuilder
import com.fuchsia.mymedicaluser.common.Results
import com.fuchsia.mymedicaluser.model.CreateUserModel
import com.fuchsia.mymedicaluser.model.GetUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class Repo @Inject constructor(private val apiBuilder: ApiBuilder){
    suspend fun createUser(
        name: String,
        email: String,
        password: String,
        address: String,
        pincode: String,
        phone_number: String
    ): Flow<Results<Response<CreateUserModel>>> = flow {

        emit(Results.Loading)
        try {
            val response = apiBuilder.api.createUser(name, email, password, address, pincode, phone_number)
            emit(Results.Success(response))

        }
        catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }

    }

    suspend fun logIn(
        email: String,
        password: String
    ):Flow<Results<Response<GetUserModel>>> = flow {
        emit(Results.Loading)

        try {
            val response = apiBuilder.api.logIn(email, password)
            emit(Results.Success(response))
            Log.d("Repo", "logIn: ${response.body()}")
        }
        catch (e: Exception) {
            emit(Results.Error(e.message.toString()))

        }

    }

    suspend fun getSpecificUser(id: String): Flow<Results<Response<GetUserModel>>> = flow {

        emit(Results.Loading)

        try {
            val response = apiBuilder.api.getSpecificUser(id)
            if (response.isSuccessful) {
                emit(Results.Success(response))
                Log.d("Repo", "getSpecificUser: ${response.body()}")
            } else {
                emit(Results.Error("Error: ${response.code()} - ${response.message()}"))
            }

        }
        catch (e: Exception) {
            emit(Results.Error(e.message.toString()))

        }

    }

    suspend fun updateUser(
        user_id: String,
        name: String,
        email: String,
        password: String,
        address: String,
        pincode: String,
        phone_number: String

    ): Flow<Results<Response<GetUserModel>>> = flow {
        emit(Results.Loading)
        try {
            val response = apiBuilder.api.updateUserDetails(user_id,name,email,password,address,pincode,phone_number)
            emit(Results.Success(response))
        }
        catch (exception: Exception){
            emit(Results.Error(exception.message.toString()))
        }
    }

}