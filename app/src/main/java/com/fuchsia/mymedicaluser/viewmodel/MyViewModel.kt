package com.fuchsia.mymedicaluser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuchsia.mymedicaluser.common.Results
import com.fuchsia.mymedicaluser.model.CreateUserModel
import com.fuchsia.mymedicaluser.model.GetUserModel
import com.fuchsia.mymedicaluser.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    private val _createUserState = MutableStateFlow(CreateUserState())
    val createUserState = _createUserState.asStateFlow()

    fun createUser(
        name: String,
        email: String,
        password: String,
        address: String,
        pincode: String,
        phone_number: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.createUser(name, email, password, address, pincode, phone_number).collect {

                when (it) {
                    is Results.Loading -> {
                        _createUserState.value = CreateUserState(isLoading = true)
                    }
                    is Results.Error -> {
                        _createUserState.value = CreateUserState(error = it.message, isLoading = false)
                    }
                    is Results.Success -> {
                        _createUserState.value = CreateUserState(data = it.data.body(), isLoading = false)
                    }
                }

            }

        }
    }
    fun resetCreateUserState() {
        _createUserState.value = CreateUserState()
    }

    private val _getSpecificUserState = MutableStateFlow(GetSpecificUserState())
    val getSpecificUserState = _getSpecificUserState.asStateFlow()

    fun getSpecificUser(user_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificUser(user_id).collect {
                when (it) {
                    is Results.Loading -> {
                        _getSpecificUserState.value = GetSpecificUserState(isLoading = true)
                    }
                    is Results.Error -> {
                        _getSpecificUserState.value =
                            GetSpecificUserState(error = it.message, isLoading = false)
                    }
                    is Results.Success -> {
                        _getSpecificUserState.value =
                            GetSpecificUserState(data = it.data.body(), isLoading = false)
                    }
                }
            }
        }
    }

    private val _logInState = MutableStateFlow(LogInState())
    val logInState = _logInState.asStateFlow()

    fun logIn(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.logIn(email, password).collect{
                when(it){
                    is Results.Loading -> {
                        _logInState.value = LogInState(isLoading = true)
                    }
                    is Results.Error -> {
                        _logInState.value = LogInState(error = it.message, isLoading = false)
                    }
                    is Results.Success -> {
                        _logInState.value = LogInState(data = it.data.body(), isLoading = false)
                    }
                }

            }

        }
    }

    fun resetLogInState() {
        _logInState.value = LogInState()
    }

    private val _GetUpdateState = MutableStateFlow(UpdateUserState())
    val GetUpdateState = _GetUpdateState.asStateFlow()

    fun updateUsers(
        user_id: String,
        name: String,
        email: String,
        password: String,
        address: String,
        phone_number: String,
        pincode: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateUser(user_id,name,email, password, address, pincode, phone_number).collect{
                when(it){
                    is Results.Loading-> {
                        _GetUpdateState.value = UpdateUserState(isLoading = true)
                    }
                    is Results.Error ->{
                        _GetUpdateState.value = UpdateUserState(error = it.message, isLoading = true)

                    }
                    is Results.Success ->{

                        _GetUpdateState.value = UpdateUserState(data = it.data.body(), isLoading = true)

                    }
                }
            }

        }
    }
}

data class CreateUserState(
    val isLoading: Boolean = false,
    val data: CreateUserModel? = null,
    val error: String? = null


)
data class GetSpecificUserState(
    val isLoading: Boolean = false,
    val data: GetUserModel? = null,
    val error: String? = null
)

data class LogInState(
    val isLoading: Boolean = false,
    val data: GetUserModel? = null,
    val error: String? = null
)

data class UpdateUserState(
    val isLoading: Boolean = false,
    val data: GetUserModel? = null,
    val error: String?= null
)