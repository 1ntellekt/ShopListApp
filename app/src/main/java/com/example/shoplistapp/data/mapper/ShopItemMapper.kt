package com.example.shoplistapp.data.mapper

import com.example.shoplistapp.data.models.ShopItemDbModel
import com.example.shoplistapp.domain.ShopItem

class ShopItemMapper {

    fun mapEntityToDbModel(shopItem: ShopItem):ShopItemDbModel =
        ShopItemDbModel(
           id = shopItem.id,
           name = shopItem.name,
           count = shopItem.count,
           enable = shopItem.enable
        )

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel):ShopItem =
        ShopItem(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name,
            count = shopItemDbModel.count,
            enable = shopItemDbModel.enable
        )

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>):List<ShopItem> = list.map {
        mapDbModelToEntity(it)
    }

}