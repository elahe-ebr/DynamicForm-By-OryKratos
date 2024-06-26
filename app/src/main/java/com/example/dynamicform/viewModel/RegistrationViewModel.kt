package com.example.dynamicform.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamicform.data.model.BaseResponseModel
import com.example.dynamicform.data.repository.RegistrationRepo
import com.example.dynamicform.util.ApiDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repo: RegistrationRepo) : ViewModel() {

    private var uiState =
        MutableStateFlow<ApiDataState<BaseResponseModel>>(ApiDataState.Loading)

    fun getRegistrationFlow():StateFlow<ApiDataState<BaseResponseModel>>  {
        viewModelScope.launch {
            repo.getRegistrationFlow().collect {
               uiState.value = it
            }
        }
        return uiState
    }
}
