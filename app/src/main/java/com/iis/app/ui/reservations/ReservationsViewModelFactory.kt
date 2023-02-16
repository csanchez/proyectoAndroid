package com.iis.app.ui.reservations





import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iis.app.data.ReservationsDataSource
import com.iis.app.data.ReservationsRepository

class ReservationsViewModelFactory:  ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservationsViewModel::class.java)) {
            return ReservationsViewModel(
                reservationsRepository = ReservationsRepository(
                    dataSource = ReservationsDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}