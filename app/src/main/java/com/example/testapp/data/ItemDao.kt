package com.example.testapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: Item)

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>
}
