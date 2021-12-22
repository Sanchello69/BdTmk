package com.vas.tmkmanager.tmkclient

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vas.tmkmanager.database.dao.ClientDao
import com.vas.tmkmanager.database.dao.OrderTmkDao

class ClientViewModelFactory(
    private val daoClientDao: ClientDao,
    private val daoOrder: OrderTmkDao,
    private val id: Int,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientViewModel::class.java)) {
            return ClientViewModel(daoClientDao, daoOrder, id, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}