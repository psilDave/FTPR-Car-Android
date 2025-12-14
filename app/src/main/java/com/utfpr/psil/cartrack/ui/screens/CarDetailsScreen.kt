package com.utfpr.psil.cartrack.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.utfpr.psil.cartrack.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailsScreen(
    @StringRes title: Int,
    @StringRes bottomButtonText: Int,
    onBackButtonPress: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(title)) },
                navigationIcon = {
                    IconButton(onClick = onBackButtonPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color(0xff1e1f57),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    scrolledContainerColor = Color(0xff1e1f57),
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff1e1f57),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(bottomButtonText),
                        modifier = Modifier.padding(vertical = 8.dp) // Aumenta a altura do botão
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "",
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.ic_no_photo),
                    contentScale = ContentScale.Fit,
                )
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Row(modifier = Modifier.padding(bottom = 16.dp)) {
                    Column {
                        Text(
                            text = stringResource(R.string.preview_car_details_screen_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.label_tf_car_year),
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.label_tf_car_plate),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.car_details_screen_card_desc),
                            modifier = Modifier
                                .padding(start = 16.dp, top = 16.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
/*                            GoogleMap(
                                modifier = Modifier.fillMaxSize(),
                                cameraPositionState = cameraPositionState,
                                properties = MapProperties(isMyLocationEnabled = true)
                            )*/

                            Text(
                                text = "Av. Monte Castelo, 222 - Centro de Santa Bárbara d'Oeste - SP",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .alpha(0.7f),
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp
                            )

                        }

                    }
                }

            }


        }
    }
}

@Composable
@Preview(showBackground = true)
fun CarDetailsScreenPreview() {
    CarDetailsScreen(
        title = R.string.preview_car_details_screen_title,
        bottomButtonText = R.string.preview_car_details_screen_button_desc,
        onBackButtonPress = {}
    )
}