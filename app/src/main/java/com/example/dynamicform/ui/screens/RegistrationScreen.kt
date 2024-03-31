package com.example.dynamicform.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import com.example.dynamicform.data.model.Node
import com.example.dynamicform.util.ApiDataState
import com.example.dynamicform.viewModel.RegistrationViewModel

@Composable
fun RegistrationScreen(viewModel: RegistrationViewModel) {

    var showLoadingToast by remember { mutableStateOf(false) }
    var showResponse by remember { mutableStateOf(false) }
    var showErrorToast by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    var nodes by remember { mutableStateOf(listOf<Node>()) }

    LaunchedEffect(Unit) {
        viewModel.uiState.collect {
            when (it) {
                is ApiDataState.Loading -> {
                    showLoadingToast = true
                }

                is ApiDataState.Success -> {
                    showResponse = true
                    it.data.ui.nodes.let { list ->
                        nodes = list
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
fun RegistrationForm(nodes: List<Node>) {
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
                if (type != "hidden" && type != "submit")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxSize(),
                        value = text,
                        label = { Text(text = item.meta.label.text) },
                        placeholder = { Text(text = item.meta.label.text, color = Color.Gray) },
                        onValueChange = {
                            text = it
                        })
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
        TextButton(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = { }) {
            Text(
                text = "Sign in",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}
