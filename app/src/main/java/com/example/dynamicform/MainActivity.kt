package com.example.dynamicform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.dynamicform.ui.navigation.SetupNavGraph
import com.example.dynamicform.viewModel.LoginViewModel
import com.example.dynamicform.viewModel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetupNavGraph(
                navController = rememberNavController(),
                hiltViewModel<RegistrationViewModel>(),
                hiltViewModel<LoginViewModel>()
            )
        }
    }
}

