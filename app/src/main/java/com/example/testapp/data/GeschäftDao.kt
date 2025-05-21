// Stand: 2025-05-21_22:30
// app/src/main/java/com/example/testapp/data/GeschäftDao.kt
package com.example.testapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GeschäftDao {
    @Insert
    suspend fun insert(geschäft: Geschäft)

    @Query("SELECT * FROM geschäfte")
    fun getAllGeschäfte(): Flow<List<Geschäft>>

    @Query("DELETE FROM geschäfte WHERE id = :geschäftId")
    suspend fun delete(geschäftId: Int)
}
