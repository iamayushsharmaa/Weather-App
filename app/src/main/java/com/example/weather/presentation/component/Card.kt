package com.example.weather.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.data.remote.respond.Weather
import com.example.weather.ui.theme.poppinsFontFamily
import kotlin.math.roundToInt

@Composable
fun DayBigCard(
    modifier: Modifier = Modifier,
    weather: Weather,
    city : String
) {
    Card(
        modifier = Modifier
            .height(542.dp)
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(38.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        )
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start =  25.dp, end = 25.dp, top = 20.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween, // Distribute items evenly with space between
                verticalAlignment = Alignment.CenterVertically // Align items vertically centered
            ) {
                Text(
                    text = "Day",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    painter = painterResource(id = R.drawable.menuhor),
                    contentDescription = "menu icon",
                    modifier = Modifier.size(25.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.adjust_svg),
                    contentDescription = "menu icon",
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Column (
                modifier = Modifier
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(R.drawable.sunicon_svg),
                    contentDescription = "sun cloud",
                    modifier = Modifier
                        .size(190.dp)
                        .padding(5.dp)
                )
                Row(
                    verticalAlignment = Alignment.Bottom, // Align the degree symbol to the top of the temperature text
                    horizontalArrangement = Arrangement.Center, // Center horizontally
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                ) {
                    Text(
                        text = "${weather.main.temp.roundToInt()}",
                        fontSize = 98.sp, // Larger size for the temperature value
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Bottom),
                        textAlign = TextAlign.Center
                    )


                    Text(
                        text = "°",
                        fontSize = 62.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Top)
                            .padding(top = 18.dp)
                    )
                }
                Text(
                    text = "Bhopal",
                    fontSize = 26.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                WeatherAddtionalInfo(
                    modifier = Modifier.padding(5.dp),
                    weatherDetailName = "Pressure",
                    weatherDetailValue = "${weather.main.pressure}"
                )
                WeatherAddtionalInfo(
                    modifier = Modifier.padding(5.dp),
                    weatherDetailName = "Humidity",
                    weatherDetailValue = "${weather.main.humidity}"
                )
                WeatherAddtionalInfo(
                    modifier = Modifier.padding(5.dp),
                    weatherDetailName = "Sea level",
                    weatherDetailValue = "${weather.main.sea_level}"
                )
            }
        }
    }
}

@Composable
fun NightSmallCard(
    weather: Weather,
    city: String
) {
    Card(
        modifier = Modifier
            .height(270.dp)
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(38.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, end = 25.dp, start = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Night",
                     fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = Color.White,
                )
                Icon(
                    painter = painterResource(id = R.drawable.menuhor),
                    contentDescription = "menu icon",
                    modifier = Modifier.size(25.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.sunicon_svg),
                    contentDescription = "sun cloud",
                    modifier = Modifier
                        .size(84.dp)
                        .padding(8.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom, // Align the degree symbol to the top of the temperature text
                horizontalArrangement = Arrangement.Center, // Center horizontally
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
            ) {
                Text(
                    text = "${weather.main.temp.roundToInt()}",
                    fontSize = 66.sp, // Larger size for the temperature value
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Bottom),
                    textAlign = TextAlign.Center
                )


                Text(
                    text = "°",
                    fontSize = 62.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Top)
                        .padding(top = 7.dp)
                )
            }
            Text(
                text = "Swipe to see details",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 20.dp,)
            )

        }
    }
}
@Composable
fun WeatherAddtionalInfo(
    modifier: Modifier = Modifier,
    weatherDetailName : String,
    weatherDetailValue : String
) {
    Column (
        modifier = Modifier
            .width(120.dp)
    ){
        Text(
            text = weatherDetailName,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = weatherDetailValue,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

    }
}