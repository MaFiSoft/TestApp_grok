// Stand: 2025-05-21_23:45
// app/src/main/java/com/example/testapp/data/ArtikelGeschaeftCrossRef.kt
package com.example.testapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "artikel_geschaeft",
    primaryKeys = ["artikelId", "geschaeftId"],
    foreignKeys = [
        ForeignKey(
            entity = Artikel::class,
            parentColumns = ["id"],
            childColumns = ["artikelId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Geschaeft::class,
            parentColumns = ["id"],
            childColumns = ["geschaeftId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["geschaeftId"])] // Index hinzugefügt
)
data class ArtikelGeschaeftCrossRef(
    val artikelId: Int,
    val geschaeftId: Int
)
