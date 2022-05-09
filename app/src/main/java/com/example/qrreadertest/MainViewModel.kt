package com.example.qrreadertest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _content = MutableLiveData<String>()
    val content: LiveData<String> = _content
    fun onMatchContent(contents: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            _content.postValue(contents)
            _isLoading.postValue(true)
            kotlin.runCatching {
                // TODO DBConnect
            }.onFailure {
                // TODO DBConnect Failure
            }.onSuccess {
                // TODO DBConnect Success
            }.also {
                _isLoading.postValue(false)
            }
        }
    }
}