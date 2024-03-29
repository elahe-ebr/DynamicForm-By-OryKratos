package com.example.dynamicform.data.model

import com.google.gson.annotations.SerializedName

data class Label(
    @SerializedName("context")
    val context: Context,
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("type")
    val type: String
)