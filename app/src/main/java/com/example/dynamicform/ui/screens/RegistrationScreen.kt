package com.example.dynamicform.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.dynamicform.data.model.Node
import com.example.dynamicform.util.ApiDataState
import com.example.dynamicform.viewModel.RegistrationViewModel

@Composable
fun RegistrationScreen(viewModel: RegistrationViewModel) {

    var showLoadingToast by remember { mutableStateOf(false) }
    var showResponse by remember { mutableStateOf(false) }
    var showErrorToast by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    var nodes = remember { mutableListOf<Node>() }

    LaunchedEffect(Unit) {
        viewModel.uiState.collect {
            when (it) {
                is ApiDataState.Loading -> {
                    showLoadingToast = true
                }

                is ApiDataState.Success -> {
                    showResponse = true
                    it.data.ui.nodes.let { list ->
                        nodes = list.toMutableList()
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
        Toast.makeText(LocalContext.current, "Loading...", Toast.LENGTH_SHORT)
            .show()
    if (showResponse)
        RegistrationForm(nodes = nodes)
    if (showErrorToast)
        Toast.makeText(LocalContext.current, errorText, Toast.LENGTH_SHORT)
            .show()
}


@Composable
fun RegistrationForm(nodes: MutableList<Node>) {
    for (item in nodes) {
        Column {
            if (item.attributes.nodeType == "input") {
                OutlinedTextField(
                    value = "",
                    label = { Text(text = item.attributes.name) },
                    onValueChange = {})
            }
        }
    }
}
