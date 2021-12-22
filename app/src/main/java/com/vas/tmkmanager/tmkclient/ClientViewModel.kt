package com.vas.tmkmanager.tmkclient

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.vas.tmkmanager.database.dao.ClientDao
import com.vas.tmkmanager.database.dao.ItemDao
import com.vas.tmkmanager.database.dao.ItemStoreroomDao
import com.vas.tmkmanager.database.dao.OrderTmkDao
import com.vas.tmkmanager.database.entities.Client
import com.vas.tmkmanager.database.entities.ItemStoreroom
import com.vas.tmkmanager.database.entities.OrderTMK
import com.vas.tmkmanager.database.entities.Staffer
import kotlinx.coroutines.*


class ClientViewModel(
    val daoClient: ClientDao,
    val daoOrder: OrderTmkDao,
    val id: Int,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    val client = daoClient.get(id)
    val orders = daoOrder.getAllClient(id)

    fun onDeleteOrder(orderTMK: OrderTMK) {
        uiScope.launch {
            deleteOrder(orderTMK)
        }
    }

    private suspend fun deleteOrder(orderTMK: OrderTMK) {
        withContext(Dispatchers.IO) {
            daoOrder.delete(orderTMK)
        }
    }

    fun onEdit(orderTMK: OrderTMK) {
        uiScope.launch {
            update(orderTMK)
        }
    }

    private suspend fun update(orderTMK: OrderTMK) {
        withContext(Dispatchers.IO) {
            daoOrder.update(orderTMK)
        }
    }

    fun onAdd(orderTMK: OrderTMK) {
        uiScope.launch {
            add(orderTMK)
        }
    }

    private suspend fun add(orderTMK: OrderTMK) {
        withContext(Dispatchers.IO) {
            daoOrder.insert(orderTMK)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}