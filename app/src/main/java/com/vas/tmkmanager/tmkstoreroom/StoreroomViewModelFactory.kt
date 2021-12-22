package com.vas.tmkmanager.tmkstoreroom

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vas.tmkmanager.database.dao.*
import com.vas.tmkmanager.tmkmain.MainViewModel

class StoreroomViewModelFactory(
    private val daoItemStoreroom: ItemStoreroomDao,
    private val daoItem: ItemDao,
    private val id: Int,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoreroomViewModel::class.java)) {
            return StoreroomViewModel(daoItemStoreroom, daoItem, id, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}