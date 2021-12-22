package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.PhysicalPerson

@Dao
interface PhysicalPersonDao : BaseDao<PhysicalPerson> {

    @Query("SELECT * FROM physical_person WHERE id_client = :key") //получение записи по id
    fun get(key: Int): LiveData<PhysicalPerson?>
}