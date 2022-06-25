package com.example.shoplistapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoplistapp.domain.ShopItem

@Entity(tableName = "shop_items")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val count:Int,
    val enable:Boolean
)
