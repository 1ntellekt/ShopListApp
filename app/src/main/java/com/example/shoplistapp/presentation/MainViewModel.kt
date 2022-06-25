package com.example.shoplistapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplistapp.data.ShopListRepositoryImpl
import com.example.shoplistapp.domain.DeleteShopItemUseCase
import com.example.shoplistapp.domain.EditShopItemUseCase
import com.example.shoplistapp.domain.GetShopListUseCase
import com.example.shoplistapp.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
        //need di
    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem){
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnabledState(shopItem: ShopItem){
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(shopItem.copy(enable = !shopItem.enable))
        }
    }

}