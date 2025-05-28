package com.fuchsia.mymedicaluser.presentation.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaluser.R
import com.fuchsia.mymedicaluser.viewmodel.ProductViewmodel

@Composable
fun OrderHistoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProductViewmodel = hiltViewModel()
) {

    val state = viewModel.getAllOrdersHistoryState.collectAsState()
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)

    val userId = loadUserId(sharedPreferences)
    LaunchedEffect(Unit) {
        viewModel.getAllOrdersHistory()
    }

    when {
        state.value.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()

            }
        }

        state.value.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.value.error!!)

            }
        }

        state.value.data != null -> {

            val data = state.value.data!!.reversed()

            if (data.isNotEmpty()) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(data.size) { index ->
                        if (userId == data[index].user_id) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
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
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "Product Name: ${data[index].product_name}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Serif,
                                        )
                                        Text(
                                            text = "User Name: ${data[index].user_name}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontFamily = FontFamily.Serif,
                                        )
                                        Text(
                                            text = "Price: ₹${data[index].total_price}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            fontFamily = FontFamily.Serif,
                                        )
                                        Text(
                                            text = "Order Date: ${data[index].date_of_order_creation}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.7f
                                            ),
                                            fontFamily = FontFamily.Serif,
                                        )
                                        Text(
                                            text = if (data[index].isApproved == 1) "Order Approved" else if (data[index].isApproved == 0) "Order is Pending" else "Order Rejected",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (data[index].isApproved == 1) Color(
                                                0xFF218C25
                                            )
                                            else if (data[index].isApproved == 0) Color(0xFFDC8502) else Color.Red,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Serif,
                                        )
                                        Text(
                                            text = "Quantity: ${data[index].quantity}",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontFamily = FontFamily.Serif,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}