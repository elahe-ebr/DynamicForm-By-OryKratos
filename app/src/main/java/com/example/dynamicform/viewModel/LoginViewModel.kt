package com.example.dynamicform.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicform.data.model.BaseResponseModel
import com.example.dynamicform.data.repository.LoginRepo
import com.example.dynamicform.util.ApiDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: LoginRepo) : ViewModel() {

    private var uiState =
        MutableStateFlow<ApiDataState<BaseResponseModel>>(ApiDataState.Loading)

     fun getLoginFlow():StateFlow<ApiDataState<BaseResponseModel>> {
        viewModelScope.launch {
            repo.getLoginFlow().collect {
                uiState.value = it
            }
        }
         return uiState
    }
}
