package com.fuchsia.mymedicaluser.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fuchsia.mymedicaluser.R
import com.fuchsia.mymedicaluser.presentation.nav.Routes

@Composable
fun DashboardScreen(modifier: Modifier = Modifier, navController: NavController) {

    Box(modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(
                    text = "Dear Customer!",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF08719A),
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color(0xFF08719A),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account",
                    tint = Color(0xFF08719A),
                    modifier = Modifier.size(30.dp).clickable {
                        navController.navigate(Routes.Home)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.home_banner),
                contentDescription = "Profile",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(15.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Text(
                text = "Welcome to Medi Hub",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
            )

            CategoryList()
        }
    }
}

@Composable
fun CategoryList() {
    val categories = listOf(
        "Medicine",
        "Equipments",
        "Devices",
        "Anesthesia",

    )
    val catImage = listOf(
        R.drawable.cat1,
        R.drawable.cat2,
        R.drawable.cat3,
        R.drawable.cat4,

    )

    Text(
        text = "________Our Services________",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        modifier = Modifier.padding(top = 30.dp)
    )

    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(categories.size) { index ->
            CategoryItem(category = categories[index], image = catImage[index])
        }
    }
}

@Composable
fun CategoryItem(category: String, image: Int) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = category,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = category,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}
