package com.example.dynamicform.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.dynamicform.R
import com.example.dynamicform.data.model.Node

@Composable
fun CustomOutlinedTextField(item:Node,text:String,showPassword:Boolean) {

    var visiblePassword by remember { mutableStateOf(showPassword) }
    var textField by remember { mutableStateOf(text) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = textField,
        label = { Text(text = item.meta.label.text) },
        visualTransformation = if (visiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
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
                    painter = if (visiblePassword) painterResource(R.drawable.ic_eye_off) else painterResource(
                        R.drawable.ic_eye_see
                    ),
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            visiblePassword = !visiblePassword
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
            textField = it
        })
}