package com.example.ageprediction.ui.repository

import com.example.ageprediction.ui.retrofit.AgifyApi
import com.example.ageprediction.ui.roomDB.NamesDatabase
import com.example.ageprediction.ui.roomDB.entities.favItem.FavItemDB
import com.example.ageprediction.ui.roomDB.entities.favItem.FavItemJsonMapper
import com.example.ageprediction.ui.roomDB.entities.searchItem.SearchItemDB
import com.example.ageprediction.ui.roomDB.entities.searchItem.SearchItemJsonMapper
import kotlinx.coroutines.flow.Flow

class NamesRepositoryImpl(private val namesDatabase: NamesDatabase,
                          private val service: AgifyApi,
                          private val searchItemJsonMapper: SearchItemJsonMapper,
                          private val favItemJsonMapper: FavItemJsonMapper
)
    : NamesRepository {

    override suspend fun loadSearchItemByNameToDB(itemName: String) {
        try {
            val searchItemJson = service.getAgeByName(itemName)
            namesDatabase.searchItemsDao().insertOneItem(searchItemJsonMapper.fromJsonToRoomDB(searchItemJson))
        } catch (e: Throwable) {
            throw NullPointerException("Error: Something get wrong while adding item to database")
        }
    }

    override suspend fun loadFavItemByNameToDB(itemName: String) {
        try {
            val favItemJson = service.getAgeByName(itemName)
            namesDatabase.favItemsDao().insertOneItem(favItemJsonMapper.fromJsonToRoomDB(favItemJson))
        } catch (e: Throwable) {
            throw NullPointerException("Error: Something get wrong while adding item to database")
        }
    }

    override fun loadSearchItemByNameFromDB(itemName: String): Flow<SearchItemDB> {
        return namesDatabase.searchItemsDao().getItemByName(itemName)
    }

    override fun loadAllSearchItemsFromDB(): Flow<List<SearchItemDB>> {
        return namesDatabase.searchItemsDao().getAllItems()
    }

    override fun loadFavItemByNameFromDB(itemName: String): Flow<FavItemDB> {
        return namesDatabase.favItemsDao().getItemByName(itemName)
    }

    override fun loadAllFavItemsFromDB(): Flow<List<FavItemDB>> {
        return namesDatabase.favItemsDao().getAllItems()
    }

    override fun deleteFavItemsFromDB(namesList: List<String>) {
        return namesDatabase.favItemsDao().deleteItemsList(namesList)
    }

}

