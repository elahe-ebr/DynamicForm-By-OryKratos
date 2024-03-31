package com.example.dynamicform.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dynamicform.ui.screens.LoginScreen
import com.example.dynamicform.ui.screens.RegistrationScreen
import com.example.dynamicform.viewModel.LoginViewModel
import com.example.dynamicform.viewModel.RegistrationViewModel

@Composable
fun SetupNavGraph(navController: NavHostController,registrationViewModel: RegistrationViewModel,loginViewModel:LoginViewModel) {
    NavHost(
        navController = navController, startDestination = Screen.Registration.route
    ) {
        composable(Screen.Registration.route) {
            RegistrationScreen(navController = navController,registrationViewModel)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController,loginViewModel)
        }
    }
}
