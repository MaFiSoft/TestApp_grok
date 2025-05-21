// Stand: 2025-05-21_22:30
// app/src/main/java/com/example/testapp/data/Kategorie.kt
package com.example.testapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kategorien")
data class Kategorie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
