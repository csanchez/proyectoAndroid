package com.iis.app.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iis.app.data.NotificationsDataSource
import com.iis.app.data.NotificationsRepository

class NotificationsViewModelFactory:  ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
            return NotificationsViewModel(
                notificationsRepository = NotificationsRepository(
                    dataSource = NotificationsDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}