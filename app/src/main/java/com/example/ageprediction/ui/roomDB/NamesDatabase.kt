package com.example.ageprediction.ui.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ageprediction.ui.roomDB.entities.favItem.FavItemDAO
import com.example.ageprediction.ui.roomDB.entities.favItem.FavItemDB
import com.example.ageprediction.ui.roomDB.entities.searchItem.SearchItemDAO
import com.example.ageprediction.ui.roomDB.entities.searchItem.SearchItemDB

@Database(entities = [SearchItemDB::class, FavItemDB::class], version = 1)
//@TypeConverters(Converters::class) <-- Use it when add some Converters methods
abstract class NamesDatabase : RoomDatabase() {
    abstract fun searchItemsDao(): SearchItemDAO
    abstract fun favItemsDao(): FavItemDAO
}