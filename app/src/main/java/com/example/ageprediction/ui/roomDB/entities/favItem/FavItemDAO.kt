package com.example.ageprediction.ui.roomDB.entities.favItem

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneItem(item: FavItemDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSomeItems(vararg nextItem: FavItemDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemsList(items: List<FavItemDB>)

    @Update
    suspend fun updateOneItem(item: FavItemDB)

    @Update
    suspend fun updateSomeItems(vararg nextItem: FavItemDB)

    @Update
    suspend fun updateAllItems(items: List<FavItemDB>)

    @Delete
    suspend fun deleteOneItem(item: FavItemDB)

    @Delete
    suspend fun deleteSomeItems(vararg nextItem: FavItemDB)

    @Delete
    suspend fun deleteAllItems(items: List<FavItemDB>)

    @Query("SELECT * FROM favItems ORDER BY name")
    fun getAllItems(): Flow<List<FavItemDB>>

    @Query("SELECT * FROM favItems WHERE name = :neededName")
    fun getItemByName(neededName: String): Flow<FavItemDB>

    @Query("DELETE FROM favItems WHERE name in (:namesList)")
    fun deleteItemsList(namesList: List<String>)

}