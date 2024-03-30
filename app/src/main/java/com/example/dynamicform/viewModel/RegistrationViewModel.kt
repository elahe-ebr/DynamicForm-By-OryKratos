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

    private var _uiState =
        MutableStateFlow<ApiDataState<BaseResponseModel>>(ApiDataState.Loading)
    val uiState: StateFlow<ApiDataState<BaseResponseModel>> = _uiState

    init {
        getRegistrationFlow()
    }

    private fun getRegistrationFlow() {
        viewModelScope.launch {
            repo.getRegistrationFlow().collect {
                _uiState.value = it
            }
        }
    }
}
