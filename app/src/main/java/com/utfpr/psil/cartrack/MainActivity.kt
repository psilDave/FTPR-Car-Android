package com.utfpr.psil.cartrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.utfpr.psil.cartrack.ui.screens.CarFormScreen
import com.utfpr.psil.cartrack.ui.theme.CarTrackTheme
import com.utfpr.psil.cartrack.ui.viewmodels.CarFormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<CarFormViewModel>()

        enableEdgeToEdge()
        setContent {
            CarFormScreen(
                title = R.string.preview_car_details_screen_button_desc,
                bottomButtonText = R.string.btn_label_edit_car,
                onBackButtonPress = { },
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarTrackTheme {
        Greeting("Android")
    }
}