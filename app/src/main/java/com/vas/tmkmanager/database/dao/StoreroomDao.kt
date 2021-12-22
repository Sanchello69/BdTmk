package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.Storeroom

@Dao
interface StoreroomDao : BaseDao<Storeroom> {

    @Query("SELECT * FROM storeroom WHERE number_storeroom = :key") //получение записи по id
    fun get(key: Int): LiveData<Storeroom?>

    @Query("SELECT * FROM storeroom ORDER BY number_storeroom") //получение всех записей
    fun getAll(): LiveData<List<Storeroom>?>
}