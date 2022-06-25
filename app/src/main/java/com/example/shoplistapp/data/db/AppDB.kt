package com.example.shoplistapp.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoplistapp.data.dao.ShopItemDao
import com.example.shoplistapp.data.models.ShopItemDbModel

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDB:RoomDatabase(){

    companion object {

        private var INSTANCE:AppDB? = null
        private var LOCK = Any()
        private const val DB_NAME = "items.db"

        fun getInstance(application: Application):AppDB {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(application,AppDB::class.java, DB_NAME).build()
                INSTANCE = db
                return db
            }
        }
    }

    abstract fun shopItemDao():ShopItemDao

}