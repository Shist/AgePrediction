package com.example.ageprediction.ui.roomDB.entities.favItem

import com.example.ageprediction.ui.retrofit.SearchItemJson

class FavItemJsonMapper {

    fun fromJsonToRoomDB(itemJson: SearchItemJson) : FavItemDB
    {
        try {
            return FavItemDB(itemJson.name!!,
                itemJson.age,
                itemJson.count)
        } catch (e: Throwable) {
            throw NullPointerException("Error: That was trying to add item with null name to database")
        }
    }

}