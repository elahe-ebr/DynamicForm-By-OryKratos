package com.example.dynamicform.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponseModel(
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("issued_at")
    val issuedAt: String,
    @SerializedName("organization_id")
    val organizationId: Any,
    @SerializedName("request_url")
    val requestUrl: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("ui")
    val ui: Ui
)