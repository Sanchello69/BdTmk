package com.vas.tmkmanager.tmkmain

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.vas.tmkmanager.database.dao.ClientDao
import com.vas.tmkmanager.database.dao.StafferDao
import com.vas.tmkmanager.database.dao.StoreroomDao
import com.vas.tmkmanager.database.entities.Client
import com.vas.tmkmanager.database.entities.ItemStoreroom
import com.vas.tmkmanager.database.entities.Staffer
import kotlinx.coroutines.*

class MainViewModel(
    val daoStaffer: StafferDao,
    val daoClient: ClientDao,
    val daoStoreroom: StoreroomDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    val staffers = daoStaffer.getAll()
    val clients = daoClient.getAll()
    val storerooms = daoStoreroom.getAll()

    fun onAdd(staffer: Staffer) {
        uiScope.launch {
            add(staffer)
        }
    }

    private suspend fun add(staffer: Staffer) {
        withContext(Dispatchers.IO) {
            daoStaffer.insert(staffer)
        }
    }

    fun onAddClient(client: Client) {
        uiScope.launch {
            addClient(client)
        }
    }

    private suspend fun addClient(client: Client) {
        withContext(Dispatchers.IO) {
            daoClient.insert(client)
        }
    }

    fun onDelete(staffer: Staffer) {
        uiScope.launch {
            delete(staffer)
        }
    }

    private suspend fun delete(staffer: Staffer) {
        withContext(Dispatchers.IO) {
            Log.d("err", staffer.serviceNumber.toString())
            daoStaffer.deleteStaffer(staffer.serviceNumber)
        }
    }

    fun onDeleteClient(client: Client) {
        uiScope.launch {
            deleteClient(client)
        }
    }

    private suspend fun deleteClient(client: Client) {
        withContext(Dispatchers.IO) {
            daoClient.delete(client)
        }
    }

    fun onEdit(key: Int, name: String, address: String, phone: String, type: String) {
        uiScope.launch {
            update(key, name, address, phone, type)
        }
    }

    private suspend fun update(key: Int, name: String, address: String, phone: String, type: String) {
        withContext(Dispatchers.IO) {
            daoClient.updateClient(key, name, address, phone, type)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}