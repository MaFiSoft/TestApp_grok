// Stand: 2025-05-21_22:30
// app/src/main/java/com/example/testapp/data/GeschaeftDao.kt
package com.example.testapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GeschaeftDao {
    @Insert
    suspend fun insert(geschaeft: Geschaeft)

    @Query("SELECT * FROM geschaefte")
    fun getAllGeschaefte(): Flow<List<Geschaeft>>

    @Query("DELETE FROM geschaefte WHERE id = :geschaeftId")
    suspend fun delete(geschaeftId: Int)
}
