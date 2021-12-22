package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.Client
import com.vas.tmkmanager.database.entities.OrderTMK

@Dao
interface OrderTmkDao : BaseDao<OrderTMK> {

    @Query("SELECT * FROM order_tmk WHERE id_order = :key") //получение записи по id
    fun get(key: Int): LiveData<OrderTMK?>

    @Query("SELECT * FROM order_tmk WHERE id_client = :key ORDER BY id_order") //получение всех записей
    fun getAllClient(key: Int): LiveData<List<OrderTMK>?>
}