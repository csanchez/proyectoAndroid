package com.iis.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iis.app.data.NewsRepository

import com.iis.app.data.Result

import com.iis.app.data.model.New

import kotlinx.coroutines.launch


class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _newsResult = MutableLiveData<NewsResult>()
    val newsResult: LiveData<NewsResult> = _newsResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun getNews( token: String ){


        viewModelScope.launch {

            val result = newsRepository.getNews (token)

            if (result is Result.Success) {

                _newsResult.value = NewsResult(success = result.data?.let { it as List<New> })


                _loading.value = false
            } else {
                if (result is Result.Error) { _loading.value = false
                    _newsResult.value = NewsResult(error = result.exception?.let { result.exception.message })
                }
            }
        }
    }
}