// Stand: 2025-05-21_22:30
// app/src/main/java/com/example/testapp/data/Geschäft.kt
package com.example.testapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geschäfte")
data class Geschäft(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
