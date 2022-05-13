package com.example.iisapp.ui.tramites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.iisapp.data.TramitesDataSource
import com.example.iisapp.data.TramitesRepository


class TramitesViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TramitesViewModel::class.java)) {
            return TramitesViewModel(
                tramitesRepository = TramitesRepository(
                    dataSource = TramitesDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}