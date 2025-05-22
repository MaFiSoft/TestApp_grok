package com.example.testapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Artikel::class, Kategorie::class, Geschaeft::class, ArtikelGeschaeftCrossRef::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatenbank : RoomDatabase() {
    abstract fun artikelDao(): ArtikelDao
    abstract fun kategorieDao(): KategorieDao
    abstract fun geschaeftDao(): GeschaeftDao
}
