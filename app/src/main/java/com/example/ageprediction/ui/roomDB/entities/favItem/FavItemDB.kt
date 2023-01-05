package com.example.ageprediction.ui.roomDB.entities.favItem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favItems")
data class FavItemDB(

    @PrimaryKey
    val name: String,

    @ColumnInfo(name = "age")
    val age: String?,

    @ColumnInfo(name = "count")
    val count: String?

)
