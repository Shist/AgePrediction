package com.example.ageprediction.ui.roomDB.entities.searchItem

import com.example.ageprediction.ui.retrofit.SearchItemJson

class SearchItemJsonMapper {

    fun fromJsonToRoomDB(itemJson: SearchItemJson) : SearchItemDB
    {
        try {
            return SearchItemDB(itemJson.name!!,
                itemJson.age,
                itemJson.count)
        } catch (e: Throwable) {
            throw NullPointerException("Error: That was trying to add item with null name to database")
        }
    }

}