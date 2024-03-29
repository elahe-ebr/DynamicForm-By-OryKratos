package com.example.dynamicform.data.repository

import com.example.dynamicform.data.api.RegistrationApiService
import com.example.dynamicform.util.CallApi
import javax.inject.Inject

class RegistrationRepo @Inject constructor(private val apiService: RegistrationApiService) : CallApi() {
    suspend fun getRegistrationFlow() = safeCallRemote {
        apiService.getRegistrationFlow()
    }
}