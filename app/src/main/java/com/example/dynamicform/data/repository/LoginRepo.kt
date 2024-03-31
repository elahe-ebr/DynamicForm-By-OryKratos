package com.example.dynamicform.data.repository

import com.example.dynamicform.data.api.LoginApiService
import com.example.dynamicform.util.CallApi
import javax.inject.Inject

class LoginRepo @Inject constructor(private val apiService: LoginApiService) : CallApi() {
    suspend fun getLoginFlow() = safeCallRemote {
        apiService.getLoginFlow()
    }
}