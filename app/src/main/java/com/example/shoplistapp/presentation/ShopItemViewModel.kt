package com.example.shoplistapp.presentation


import android.app.Application
import androidx.lifecycle.*
import com.example.shoplistapp.data.ShopListRepositoryImpl
import com.example.shoplistapp.domain.AddShopItemUseCase
import com.example.shoplistapp.domain.EditShopItemUseCase
import com.example.shoplistapp.domain.GetShopItemUseCase
import com.example.shoplistapp.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    private val _errorInputCount = MutableLiveData<Boolean>()
    private val _currentShopItem = MutableLiveData<ShopItem>()
    private val _closeScreen = MutableLiveData<Unit>()

    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    val currentShopItem: LiveData<ShopItem>
        get() = _currentShopItem

    val closeScreen: LiveData<Unit>
        get() = _closeScreen

    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _currentShopItem.value = item
        }
    }

    fun editShopItem(nameInput: String?, countInput: String?) {
        val name = parseName(nameInput)
        val count = parseCount(countInput)
        if (validateInput(name, count)) {
            _currentShopItem.value?.let {
                viewModelScope.launch {
                    editShopItemUseCase.editShopItem(it.copy(name = name, count = count))
                    finishWork()
                }
            }
        }
    }

    fun addShopItem(nameInput: String?, countInput: String?) {
        val name = parseName(nameInput)
        val count = parseCount(countInput)
        if (validateInput(name, count)) {
            viewModelScope.launch {
                val shopItem = ShopItem(name, count, true)
                addShopItemUseCase.addShopItem(shopItem)
                finishWork()
            }
        }
    }

    private fun parseName(nameInput: String?): String {
        return nameInput?.trim() ?: ""
    }

    private fun parseCount(countInput: String?): Int {
        return try {
            countInput?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        return when {
            name.isBlank() -> {
                _errorInputName.value = true
                false
            }
            count <= 0 -> {
                _errorInputCount.value = true
                false
            }
            else -> true
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _closeScreen.value = Unit
    }

}