package com.iis.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iis.app.data.NewsDataSource
import com.iis.app.data.NewsRepository




class HomeViewModelFactory:  ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                newsRepository = NewsRepository(
                    dataSource = NewsDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}