package com.example.dynamicform.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import com.example.dynamicform.viewModel.LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {

    var showLoadingToast by remember { mutableStateOf(false) }
    var showResponse by remember { mutableStateOf(false) }
    var showErrorToast by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    var nodes by remember { mutableStateOf(listOf<Node>()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getLoginFlow().collect {
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
        LoginForm(navController, nodes)
    if (showErrorToast)
        ShowToast(errorText, context)
}

@Composable
fun LoginForm(navController: NavHostController, nodes: List<Node>) {

    var clickedSignUp by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, end = 20.dp, start = 20.dp, bottom = 20.dp)
    ) {
        Text(
            text = "Sign in form",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge
        )
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(nodes) { item ->
                val text by remember { mutableStateOf("") }
                val type by remember { mutableStateOf(item.attributes.type) }
                val showPassword by remember { mutableStateOf(item.attributes.type != "password") }

                if (type != "hidden" && type != "submit")
                    CustomOutlinedTextField(item, text, showPassword)
            }
        }
        Button(modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
            .height(45.dp), onClick = { }) {
            Text(text = "Sign in")
        }
        Text(
            text = "Do you not have an account?",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        TextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { clickedSignUp = true }) {
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    LaunchedEffect(clickedSignUp) {
        if (clickedSignUp)
            navController.navigate(Screen.Registration.route)
    }

}
