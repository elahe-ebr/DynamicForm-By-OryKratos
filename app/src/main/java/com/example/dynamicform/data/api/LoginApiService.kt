package com.example.dynamicform.data.api

import com.example.dynamicform.data.model.BaseResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface LoginApiService {
    @GET("self-service/login/api")
    suspend fun getLoginFlow(): Response<BaseResponseModel>
}