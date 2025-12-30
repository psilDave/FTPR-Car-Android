package com.utfpr.psil.cartrack.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.utfpr.psil.cartrack.ui.model.AppScreens
import com.utfpr.psil.cartrack.ui.screens.CarListScreen
import com.utfpr.psil.cartrack.ui.screens.LoginScreen
import com.utfpr.psil.cartrack.ui.screens.SmsCodeVerificationScreen
import com.utfpr.psil.cartrack.ui.screens.SmsNumberScreen
import com.utfpr.psil.cartrack.ui.viewmodels.AuthViewModel

@Composable
fun CarTrackApp() {

    val navController = rememberNavController()

    val authViewModel: AuthViewModel = hiltViewModel()
    val authUiState by authViewModel.uiAuthState.collectAsState()

    NavHost(
        navController,
        startDestination = if (authUiState.isUserLoggedIn) AppScreens.CarList.name else
            AppScreens.Login.name
    ) {

        composable(AppScreens.Login.name) {
            LoginScreen(
                authViewModel = authViewModel,
                onSuccessfullyLoginOnGoogle = { navController.navigate(AppScreens.CarList.name) },
                onSmsLogin = { navController.navigate(AppScreens.SmsNumber.name) }
            )
        }
        composable(AppScreens.SmsNumber.name) {
            SmsNumberScreen(
                authViewModel = authViewModel,
                onSendCodeClick = { navController.navigate(AppScreens.SmsCodeVerification.name) },
                onBackButtonPress = { navController.navigate(AppScreens.Login.name) }
            )
        }
        composable(AppScreens.SmsCodeVerification.name) {
            SmsCodeVerificationScreen(
                onBackButtonPress = { navController.navigate(AppScreens.Login.name) },
                onSuccessfulVerification = { navController.navigate(AppScreens.CarList.name) },
                authViewModel = authViewModel
            )
        }
        composable(AppScreens.CarList.name) {
            CarListScreen(
                onLogoutButtonPress = {
                    navController.navigate(AppScreens.Login.name) { popUpTo(0) }
                },
                onAddNewCarButtonPress = { navController.navigate(AppScreens.CarForm.name) },
                authViewModel = authViewModel
            )
        }

    }
}