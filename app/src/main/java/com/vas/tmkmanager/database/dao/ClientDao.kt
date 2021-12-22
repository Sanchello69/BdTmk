package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.Client

@Dao
interface ClientDao : BaseDao<Client> {

    @Query("SELECT * FROM client WHERE id_client = :key") //получение записи по id
    fun get(key: Int): LiveData<Client?>

    @Query("SELECT * FROM client ORDER BY name_client") //получение всех записей
    fun getAll(): LiveData<List<Client>?>

    @Query("SELECT * FROM client ORDER BY id_client DESC LIMIT 1")
    fun getClient(): Client?

    @Query("UPDATE client SET name_client = :name , address_client = :address , phone_client = :phone , type_client = :type WHERE id_client = :key")
    fun updateClient(key: Int, name: String, address: String, phone: String, type: String)
}