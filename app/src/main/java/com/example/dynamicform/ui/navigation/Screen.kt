package com.example.dynamicform.ui.navigation

sealed class Screen(val route: String) {

    data object Registration : Screen("registration")
    data object Login :Screen("login")
}
