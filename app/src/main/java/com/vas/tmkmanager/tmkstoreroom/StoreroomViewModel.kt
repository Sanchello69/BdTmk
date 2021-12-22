package com.vas.tmkmanager.tmkstoreroom

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vas.tmkmanager.database.dao.*
import com.vas.tmkmanager.database.entities.ItemStoreroom
import com.vas.tmkmanager.database.entities.Staffer
import kotlinx.coroutines.*

class StoreroomViewModel(
    val daoItemStoreroom: ItemStoreroomDao,
    val daoItem: ItemDao,
    val id: Int,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    val itemStorerooms = daoItemStoreroom.getItemStoreroom(id)
    val items = daoItem.getAllName()
    val itemsData = daoItem.getAll()

    fun onEdit(itemStoreroom: ItemStoreroom) {
        uiScope.launch {
            update(itemStoreroom)
        }
    }

    fun onAdd(itemStoreroom: ItemStoreroom) {
        uiScope.launch {
            add(itemStoreroom)
        }
    }

    private suspend fun add(itemStoreroom: ItemStoreroom) {
        withContext(Dispatchers.IO) {
            daoItemStoreroom.insert(itemStoreroom)
        }
    }

    private suspend fun update(itemStoreroom: ItemStoreroom) {
        withContext(Dispatchers.IO) {
            daoItemStoreroom.update(itemStoreroom)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}