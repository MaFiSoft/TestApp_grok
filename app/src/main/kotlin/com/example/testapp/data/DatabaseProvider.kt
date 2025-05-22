// app/src/main/java/com/example/testapp/data/DatabaseProvider.kt
package com.example.testapp.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    fun getDatabase(context: Context): AppDatenbank {
        return Room.databaseBuilder(
            context,
            AppDatenbank::class.java,
            "testapp-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
