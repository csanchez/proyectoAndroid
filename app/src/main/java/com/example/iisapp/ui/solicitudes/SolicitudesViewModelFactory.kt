package com.example.iisapp.ui.solicitudes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.iisapp.data.SolicitudesDataSource
import com.example.iisapp.data.SolicitudesRepository

class SolicitudesViewModelFactory: ViewModelProvider.Factory  {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SolicitudesViewModel::class.java)) {
            return SolicitudesViewModel(
                solicitudesRepository = SolicitudesRepository(
                    dataSource = SolicitudesDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}