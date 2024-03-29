package com.example.dynamicform.data.api

import com.example.dynamicform.data.model.BaseResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface RegistrationApiService {
    @GET("self-service/registration/api")
    suspend fun getRegistrationFlow(): Response<BaseResponseModel>
}