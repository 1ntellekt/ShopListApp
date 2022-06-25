package com.example.shoplistapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shoplistapp.data.db.AppDB
import com.example.shoplistapp.data.mapper.ShopItemMapper
import com.example.shoplistapp.domain.ShopItem
import com.example.shoplistapp.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopItemDao = AppDB.getInstance(application).shopItemDao()
    private val mapper = ShopItemMapper()

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopItemDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopItemDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopItemDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        val dbModel = shopItemDao.getShopItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

/*    override fun getShopList(): LiveData<List<ShopItem>> =
        MediatorLiveData<List<ShopItem>>().apply {
            addSource(shopItemDao.getShopList()){
                value = mapper.mapListDbModelToListEntity(it)
            }
        }*/

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(shopItemDao.getShopList()){
        mapper.mapListDbModelToListEntity(it)
    }

}

// test impl for clean architecture