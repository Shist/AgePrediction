package com.example.ageprediction.ui.roomDB.entities.searchItem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchItems")
data class SearchItemDB(

    @PrimaryKey
    val name: String,

    @ColumnInfo(name = "age")
    val age: String?,

    @ColumnInfo(name = "count")
    val count: String?

)
