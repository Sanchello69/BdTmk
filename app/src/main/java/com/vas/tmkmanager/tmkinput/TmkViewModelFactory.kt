package com.vas.tmkmanager.tmkinput

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vas.tmkmanager.database.dao.StafferDao

class TmkViewModelFactory(
    private val daoStafferDao: StafferDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TmkViewModel::class.java)) {
            return TmkViewModel(daoStafferDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}