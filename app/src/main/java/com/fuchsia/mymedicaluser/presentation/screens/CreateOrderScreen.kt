package com.fuchsia.mymedicaluser.presentation.screens

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaluser.R
import com.fuchsia.mymedicaluser.presentation.nav.Routes
import com.fuchsia.mymedicaluser.viewmodel.MyViewModel
import com.fuchsia.mymedicaluser.viewmodel.ProductViewmodel
import kotlinx.coroutines.launch

@Composable
fun CreateOrderScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewmodel: ProductViewmodel = hiltViewModel(),
    viewmodeluser: MyViewModel = hiltViewModel(),
    productId: String,
    productName: String,
    category: String,
    price: Float,
    stock: Int

) {

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)

    val state = viewmodeluser.getSpecificUserState.collectAsState()

    val user_id = loadUserId(sharedPreferences)


    LaunchedEffect(Unit) {
        if (user_id != null) {
            viewmodeluser.getSpecificUser(user_id)
        }
    }
    when {
        state.value.isLoading -> {
            Box(modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(text = "Loading")

            }
        }

        state.value.error != null -> {
            Text(text = state.value.error.toString())
        }

        state.value.data != null -> {
            if (state.value.data != null) {

                val data = state.value.data

                val messageState = remember { mutableStateOf("") }
                val quantityState = remember { mutableStateOf("") }
                val nameState = remember { mutableStateOf("") }
                val receiverPhoneNumber = remember { mutableStateOf("") }
                val addressState = remember { mutableStateOf("") }


                Column(
                    Modifier.fillMaxSize().padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.pdtimage),
                                contentDescription = "Product Image",
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(100.dp)
                                    .padding(end = 10.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = productName,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Category: ${category}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontFamily = FontFamily.Serif,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Price: ₹${price.toString()}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = FontFamily.Serif,


                                    )
                                Text(
                                    text = "Stock: ${stock.toString()}",
                                    fontFamily = FontFamily.Serif,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (stock > 0) Color(0xFF4CAF50) else Color(
                                        0xFFFF5722
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = nameState.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        onValueChange = {
                            nameState.value = it
                        },
                        label = {
                            Text(text = "Enter Name",
                                    fontFamily = FontFamily.Serif)

                        }
                    )

                    OutlinedTextField(
                        value = addressState.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        onValueChange = {
                            addressState.value = it
                        },
                        label = {
                            Text(text = "Enter Address",
                                fontFamily = FontFamily.Serif)

                        }
                    )
                    OutlinedTextField(
                        value = receiverPhoneNumber.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        onValueChange = {
                            receiverPhoneNumber.value = it
                        },
                        label = {
                            Text(text = "Receiver's Phone Number",
                                fontFamily = FontFamily.Serif)

                        }
                    )
                    OutlinedTextField(
                        value = quantityState.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        onValueChange = {
                            quantityState.value = it
                        },
                        label = {
                            Text(text = "Enter Quantity",
                                fontFamily = FontFamily.Serif)

                        }
                    )
                    OutlinedTextField(
                        value = messageState.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        onValueChange = {
                            messageState.value = it
                        },
                        label = {
                            Text(text = "Enter Message",
                                fontFamily = FontFamily.Serif)

                        }
                    )

                    val totalPrice = quantityState.value.toIntOrNull()?.let { it * price } ?: 0f

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {

                        if(quantityState.value.isNotEmpty() && messageState.value.isNotEmpty()
                            && (quantityState.value.toInt() in 1..stock)
                            && receiverPhoneNumber.value.isNotEmpty()
                            && addressState.value.isNotEmpty()
                            && nameState.value.isNotEmpty()){
                            coroutineScope.launch {
                                if (data != null) {
                                    Toast.makeText(context, data.name, Toast.LENGTH_SHORT).show()
                                    viewmodel.createOrder(
                                        user_name = nameState.value,
                                        quantity = quantityState.value,
                                        product_price = price.toString(),
                                        product_id = productId,
                                        total_price = totalPrice.toString(),
                                        product_name = productName,
                                        message = messageState.value,
                                        category = category,
                                        user_id = user_id.toString(),
                                        email = data.email,
                                        address = data.address,
                                        phone_number = receiverPhoneNumber.value
                                    )
                                }
                            }
                            Toast.makeText(context, "Order Placed and Pending for Approval.", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }else{
                            Toast.makeText(context, "Enter All the Details", Toast.LENGTH_SHORT).show()
                        }


                    }) {
                        Text(text = "Order Now",
                            fontFamily = FontFamily.Serif)
                    }

                }
            }

        }

    }
}

