package com.example.dynamicform.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

@Composable
fun ShowToast(text: String, context: Context) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}


