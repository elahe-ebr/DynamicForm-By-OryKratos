package com.example.dynamicform.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dynamicform.R
import com.example.dynamicform.data.model.Node
import com.example.dynamicform.ui.ShowToast
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, end = 20.dp, start = 20.dp, bottom = 20.dp)
    ) {
        Text(
            text = "Sign up form",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge
        )
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(nodes) { item ->
                var text by remember { mutableStateOf("") }
                val type by remember { mutableStateOf(item.attributes.type) }
                var showPassword by remember { mutableStateOf(item.attributes.type != "password") }
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
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = text,
                            label = { Text(text = item.meta.label.text) },
                            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            leadingIcon = {
                                if (item.attributes.required)
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "",
                                        tint = Color.Red,
                                        modifier = Modifier.size(18.dp)
                                    )
                            },
                            trailingIcon = {
                                if (item.attributes.type == "password")
                                    Icon(
                                        painter = if (showPassword) painterResource(R.drawable.ic_eye_off) else painterResource(
                                            R.drawable.ic_eye_see
                                        ),
                                        contentDescription = "",
                                        tint = Color.Gray,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                showPassword = !showPassword
                                            }
                                    )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = when (item.attributes.type) {
                                    "text" -> KeyboardType.Text
                                    "number" -> KeyboardType.Number
                                    "tel" -> KeyboardType.Phone
                                    "email" -> KeyboardType.Email
                                    else -> KeyboardType.Text
                                }
                            ),
                            onValueChange = {
                                text = it
                            })
                }
            }
        }

        Button(modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
            .height(45.dp), onClick = { }) {
            Text(text = "Sign up")
        }
        Text(
            text = "Already a member?",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        TextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { clickedSignIn = true }) {
            Text(
                text = "Sign in",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    LaunchedEffect(clickedSignIn) {
        if (clickedSignIn)
            navController.navigate(Screen.Login.route)
    }
}

