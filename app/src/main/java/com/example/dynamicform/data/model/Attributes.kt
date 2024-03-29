package com.example.dynamicform.data.model

import com.google.gson.annotations.SerializedName


data class Attributes(
    @SerializedName("autocomplete")
    val autocomplete: String,
    @SerializedName("disabled")
    val disabled: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_type")
    val nodeType: String,
    @SerializedName("required")
    val required: Boolean,
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: String
)