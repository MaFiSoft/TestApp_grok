package com.example.testapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String? = null,
    val stores: String? = null // Komma-separierte Geschäfte
)
