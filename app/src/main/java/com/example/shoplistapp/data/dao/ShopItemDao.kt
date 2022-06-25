package com.example.shoplistapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoplistapp.data.models.ShopItemDbModel

@Dao
interface ShopItemDao {

    @Query("SELECT * FROM shop_items")
    fun getShopList():LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE FROM shop_items Where id=:shopItemId")
    fun deleteShopItem(shopItemId:Int)

    @Query("SELECT * FROM shop_items Where id=:shopItemId LIMIT 1")
    fun getShopItem(shopItemId: Int):ShopItemDbModel

}