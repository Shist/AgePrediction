package com.example.ageprediction.ui.repository

import com.example.ageprediction.ui.roomDB.entities.favItem.FavItemDB
import com.example.ageprediction.ui.roomDB.entities.searchItem.SearchItemDB
import kotlinx.coroutines.flow.Flow

interface NamesRepository {

    suspend fun loadSearchItemByNameToDB(itemName: String)

    suspend fun loadFavItemByNameToDB(itemName: String)

    fun loadSearchItemByNameFromDB(itemName: String): Flow<SearchItemDB>

    fun loadAllSearchItemsFromDB(): Flow<List<SearchItemDB>>

    fun loadFavItemByNameFromDB(itemName: String): Flow<FavItemDB>

    fun loadAllFavItemsFromDB(): Flow<List<FavItemDB>>

    fun deleteFavItemsFromDB(namesList: List<String>)

}