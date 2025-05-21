// Stand: 2025-05-21_22:30
// app/src/main/java/com/example/testapp/data/ArtikelGeschäftCrossRef.kt
package com.example.testapp.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "artikel_geschäft",
    primaryKeys = ["artikelId", "geschäftId"],
    foreignKeys = [
        ForeignKey(
            entity = Artikel::class,
            parentColumns = ["id"],
            childColumns = ["artikelId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Geschäft::class,
            parentColumns = ["id"],
            childColumns = ["geschäftId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ArtikelGeschäftCrossRef(
    val artikelId: Int,
    val geschäftId: Int
)
