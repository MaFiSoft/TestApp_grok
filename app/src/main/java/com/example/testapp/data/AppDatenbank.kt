// Stand: 2025-05-21_22:30
// app/src/main/java/com/example/testapp/data/AppDatenbank.kt
package com.example.testapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Artikel::class, Kategorie::class, Geschaeft::class, ArtikelGeschaeftCrossRef::class], version = 1)
abstract class AppDatenbank : RoomDatabase() {
    abstract fun artikelDao(): ArtikelDao
    abstract fun kategorieDao(): KategorieDao
    abstract fun geschaeftDao(): GeschaeftDao
}
