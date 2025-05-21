// Stand: 2025-05-21_22:30
// app/src/main/java/com/example/testapp/data/Artikel.kt
package com.example.testapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "artikel",
    foreignKeys = [ForeignKey(
        entity = Kategorie::class,
        parentColumns = ["id"],
        childColumns = ["kategorieId"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Artikel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val kategorieId: Int? = null,
    val gekauft: Boolean = false // Für Abhakfunktion
)
