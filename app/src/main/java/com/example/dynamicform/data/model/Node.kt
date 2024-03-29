package com.example.dynamicform.data.model

import com.google.gson.annotations.SerializedName

data class Node(
    @SerializedName("attributes")
    val attributes: Attributes,
    @SerializedName("group")
    val group: String,
    @SerializedName("messages")
    val messages: List<Any>,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("type")
    val type: String
)