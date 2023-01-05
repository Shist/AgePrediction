package com.example.ageprediction.ui.roomDB.entities.searchItem

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneItem(item: SearchItemDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSomeItems(vararg nextItem: SearchItemDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemsList(items: List<SearchItemDB>)

    @Update
    suspend fun updateOneItem(item: SearchItemDB)

    @Update
    suspend fun updateSomeItems(vararg nextItem: SearchItemDB)

    @Update
    suspend fun updateAllItems(items: List<SearchItemDB>)

    @Delete
    suspend fun deleteOneItem(item: SearchItemDB)

    @Delete
    suspend fun deleteSomeItems(vararg nextItem: SearchItemDB)

    @Delete
    suspend fun deleteAllItems(items: List<SearchItemDB>)

    @Query("SELECT * FROM searchItems ORDER BY name")
    fun getAllItems(): Flow<List<SearchItemDB>>

    @Query("SELECT * FROM searchItems WHERE name = :neededName")
    fun getItemByName(neededName: String): Flow<SearchItemDB>

}