// Stand: 2025-05-21_22:30
// app/src/main/java/com/example/testapp/data/KategorieDao.kt
package com.example.testapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface KategorieDao {
    @Insert
    suspend fun insert(kategorie: Kategorie)

    @Query("SELECT * FROM kategorien")
    fun getAllKategorien(): Flow<List<Kategorie>>

    @Query("DELETE FROM kategorien WHERE id = :kategorieId")
    suspend fun delete(kategorieId: Int)
}
