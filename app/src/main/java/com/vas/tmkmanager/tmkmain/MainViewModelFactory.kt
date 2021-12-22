package com.vas.tmkmanager.tmkmain

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vas.tmkmanager.database.dao.ClientDao
import com.vas.tmkmanager.database.dao.StafferDao
import com.vas.tmkmanager.database.dao.StoreroomDao
import com.vas.tmkmanager.tmkinput.TmkViewModel

class MainViewModelFactory(
    private val daoStafferDao: StafferDao,
    private val daoClientDao: ClientDao,
    private val daoStoreroom: StoreroomDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(daoStafferDao, daoClientDao, daoStoreroom, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}