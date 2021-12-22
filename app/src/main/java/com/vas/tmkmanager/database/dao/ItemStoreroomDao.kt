package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.Item
import com.vas.tmkmanager.database.entities.Client
import com.vas.tmkmanager.database.entities.ItemStoreroom
import com.vas.tmkmanager.tmkstoreroom.ItemStoreroomForList

@Dao
interface ItemStoreroomDao : BaseDao<ItemStoreroom> {

    @Query("SELECT * FROM item_storeroom WHERE id_item = :key") //получение записи по id
    fun get(key: Int): LiveData<ItemStoreroom?>

    @Query("SELECT * FROM item_storeroom ORDER BY quanity_storeroom") //получение всех записей
    fun getAll(): LiveData<List<ItemStoreroom>?>

    @Query("SELECT * FROM item_storeroom WHERE number_storeroom = :key") //получение всех записей
    fun getAllId(key: Int): LiveData<List<ItemStoreroom>?>

    @Query("SELECT item.id_item, item.name, item_storeroom.quanity_storeroom FROM item, item_storeroom WHERE item.id_item = item_storeroom.id_item AND number_storeroom = :key ORDER BY quanity_storeroom DESC")
    fun getItemStoreroom(key: Int): LiveData<List<ItemStoreroomForList>?>
}