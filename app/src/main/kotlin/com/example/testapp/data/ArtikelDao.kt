// Stand: 2025-05-21_22:30
// app/src/main/java/com/example/testapp/data/ArtikelDao.kt
package com.example.testapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtikelDao {
    @Insert
    suspend fun insert(artikel: Artikel)

    @Insert
    suspend fun insertArtikelGeschaeftCrossRef(crossRef: ArtikelGeschaeftCrossRef)

    @Query("SELECT * FROM artikel")
    fun getAllArtikel(): Flow<List<Artikel>>

    @Query("DELETE FROM artikel WHERE id = :artikelId")
    suspend fun delete(artikelId: Int)
}
