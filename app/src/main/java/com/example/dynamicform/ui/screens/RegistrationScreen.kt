package com.example.dynamicform.ui.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.dynamicform.util.ApiDataState
import com.example.dynamicform.viewModel.RegistrationViewModel

@Composable
fun RegistrationScreen(viewModel: RegistrationViewModel) {

    var showLoadingToast by remember { mutableStateOf(false) }
    var showErrorToast by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.response.collect {
            when (it) {
                is ApiDataState.Loading -> {
                    showLoadingToast = true
                }

                is ApiDataState.Success -> {
                    it.data.ui.nodes.let {
                    }
                }

                is ApiDataState.Error -> {
                    showErrorToast = true
                    it.e.message?.let { message ->
                        errorText = message
                    }
                }
            }
        }
    }

    if (showLoadingToast)
        Toast.makeText(LocalContext.current, "Loading...", Toast.LENGTH_SHORT).show()

    if (showErrorToast)
        Toast.makeText(LocalContext.current, errorText, Toast.LENGTH_SHORT).show()

}
