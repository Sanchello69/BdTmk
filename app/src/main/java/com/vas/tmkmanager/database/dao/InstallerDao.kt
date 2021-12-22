package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.Installer

@Dao
interface InstallerDao : BaseDao<Installer> {

    @Query("SELECT * FROM installer WHERE service_number = :key") //получение записи по id
    fun get(key: Int): LiveData<Installer?>
}