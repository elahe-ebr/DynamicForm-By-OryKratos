package com.example.dynamicform.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dynamicform.data.model.Node
import com.example.dynamicform.ui.components.CustomOutlinedTextField
import com.example.dynamicform.ui.components.ShowToast
import com.example.dynamicform.ui.navigation.Screen
import com.example.dynamicform.util.ApiDataState
import com.example.dynamicform.viewModel.RegistrationViewModel

@Composable
fun RegistrationScreen(navController: NavHostController, viewModel: RegistrationViewModel) {

    var showLoadingToast by remember { mutableStateOf(false) }
    var showResponse by remember { mutableStateOf(false) }
    var showErrorToast by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    var nodes by remember { mutableStateOf(listOf<Node>()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getRegistrationFlow().collect {
            when (it) {
                is ApiDataState.Loading -> {
                    showLoadingToast = true
                }

                is ApiDataState.Success -> {
                    showResponse = true
                    showLoadingToast = false
                    it.data.ui.nodes.let { list ->
                        nodes = list
                    }
                }

                is ApiDataState.Error -> {
                    showErrorToast = true
                    showLoadingToast = false
                    it.e.message?.let { message ->
                        errorText = message
                    }
                }
            }
        }
    }

    if (showLoadingToast)
        ShowToast("Loading...", context)
    if (showResponse)
        RegistrationForm(navController, nodes)
    if (showErrorToast)
        ShowToast(errorText, context)
}


@Composable
fun RegistrationForm(navController: NavHostController, nodes: List<Node>) {

    var clickedSignIn by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, end = 20.dp, start = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item() {
            Text(
                text = "Sign up form",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        items(nodes) { item ->
            val text by remember { mutableStateOf("") }
            val type by remember { mutableStateOf(item.attributes.type) }
            val showPassword by remember { mutableStateOf(item.attributes.type != "password") }
            var checkedState by remember { mutableStateOf(true) }

            if (type != "hidden" && type != "submit") {
                if (type == "checkbox") {
                    Row(verticalAlignment = Alignment.CenterVertically)
                    {
                        Checkbox(
                            checked = checkedState,
                            onCheckedChange = { checkedState = it },
                        )
                        Text(text = item.meta.label.text)
                    }
                } else
                    CustomOutlinedTextField(item, text, showPassword)

            }
        }
        item {
            Button(modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .height(45.dp), onClick = { }) {
                Text(text = "Sign up")
            }
            Text(
                text = "Already a member?",
                modifier = Modifier
                    .padding(top = 10.dp),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
            TextButton(
                onClick = { clickedSignIn = true }) {
                Text(
                    text = "Sign in",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    LaunchedEffect(clickedSignIn) {
        if (clickedSignIn)
            navController.navigate(Screen.Login.route)
    }
}

