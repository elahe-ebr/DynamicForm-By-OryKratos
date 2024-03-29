package com.example.dynamicform.data.model

import com.google.gson.annotations.SerializedName

data class Ui(
    @SerializedName("action")
    val action: String,
    @SerializedName("method")
    val method: String,
    @SerializedName("nodes")
    val nodes: List<Node>
)