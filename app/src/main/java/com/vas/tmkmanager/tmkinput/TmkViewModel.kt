package com.vas.tmkmanager.tmkinput

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.vas.tmkmanager.database.dao.ClientDao
import com.vas.tmkmanager.database.dao.StafferDao
import com.vas.tmkmanager.database.entities.Client
import com.vas.tmkmanager.database.entities.Staffer
import kotlinx.coroutines.*

class TmkViewModel(
    val daoStaffer: StafferDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _navigateToMain = MutableLiveData<Staffer>()
    val navigateToMain: LiveData<Staffer>
        get() = _navigateToMain

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onClickCheck(id: Int, name: String) { //клик
        uiScope.launch {
            _navigateToMain.value = stafferCheck(id, name)
        }
    }

    private suspend fun stafferCheck(id: Int, name: String) : Staffer? {
        return withContext(Dispatchers.IO) {
            val staffer = daoStaffer.getСheck(id, name)
            staffer
        }
    }

    fun doneNavigating() {
        _navigateToMain.value = null
    }
}