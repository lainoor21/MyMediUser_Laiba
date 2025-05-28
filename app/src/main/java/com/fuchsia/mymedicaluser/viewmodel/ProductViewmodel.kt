package com.fuchsia.mymedicaluser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuchsia.mymedicaluser.common.Results
import com.fuchsia.mymedicaluser.model.CreateOrderResponse
import com.fuchsia.mymedicaluser.model.GetAllOrdersResponse
import com.fuchsia.mymedicaluser.model.ProductResponse
import com.fuchsia.mymedicaluser.repo.ProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewmodel @Inject constructor(private val productRepo: ProductRepo) : ViewModel() {

    private val _getAllProductsState= MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductsState.asStateFlow()

    suspend fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.getAllProducts().collect {
                when (it) {
                    is Results.Loading -> {
                        _getAllProductsState.value = GetAllProductsState(isLoading = true)
                    }

                    is Results.Error -> {
                        _getAllProductsState.value = GetAllProductsState(error = it.message)

                    }

                    is Results.Success -> {
                        _getAllProductsState.value = GetAllProductsState(data = it.data.body())
                    }

                }

            }

        }
    }
    private val _createOrderState = MutableStateFlow(CreateOrderState())
    val createOrderState = _createOrderState.asStateFlow()

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

    ){
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.createOrder(
                user_name, quantity, product_price, total_price, product_name, message, category, user_id, email, address, phone_number, product_id
            ).collect{
                when(it){
                    is Results.Loading -> {
                        _createOrderState.value = CreateOrderState(isLoading = true)
                    }
                    is Results.Error -> {
                        _createOrderState.value = CreateOrderState(error = it.message)
                    }
                    is Results.Success -> {
                        _createOrderState.value = CreateOrderState(data = it.data.body())
                    }
                }
            }

        }
    }


    private val _getAllOrdersHistoryState= MutableStateFlow(GetAllOrdersState())
    val getAllOrdersHistoryState = _getAllOrdersHistoryState.asStateFlow()

    suspend fun getAllOrdersHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.get_AllOrdersHistory().collect {
                when (it) {
                    is Results.Loading -> {
                        _getAllOrdersHistoryState.value = GetAllOrdersState(isLoading = true)
                    }

                    is Results.Error -> {
                        _getAllOrdersHistoryState.value = GetAllOrdersState(error = it.message)

                    }

                    is Results.Success -> {
                        _getAllOrdersHistoryState.value = GetAllOrdersState(data = it.data.body())
                    }

                }

            }

        }
    }

}

data class GetAllProductsState(
    val isLoading: Boolean = false,
    val data: ProductResponse? = null,
    val error: String?= null
)

data class CreateOrderState(
    val isLoading: Boolean = false,
    val data: CreateOrderResponse? = null,
    val error: String?= null

)
data class GetAllOrdersState(
    val isLoading: Boolean = false,
    val data: GetAllOrdersResponse? = null,
    val error: String?= null


)