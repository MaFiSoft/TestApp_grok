// Stand: 2025-05-21_22:30
// app/src/main/java/com/example/testapp/ui/ArtikelViewModel.kt
package com.example.testapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.Artikel
import com.example.testapp.data.ArtikelDao
import com.example.testapp.data.KategorieDao
import com.example.testapp.data.GeschäftDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ArtikelViewModel(
    private val artikelDao: ArtikelDao,
    private val kategorieDao: KategorieDao,
    private val geschäftDao: GeschäftDao
) : ViewModel() {
    val artikel: StateFlow<List<Artikel>> = artikelDao.getAllArtikel()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val kategorien: StateFlow<List<Kategorie>> = kategorieDao.getAllKategorien()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val geschäfte: StateFlow<List<Geschäft>> = geschäftDao.getAllGeschäfte()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    suspend fun addArtikel(name: String, kategorieId: Int? = null) {
        artikelDao.insert(Artikel(name = name, kategorieId = kategorieId))
    }

    suspend fun addKategorie(name: String) {
        kategorieDao.insert(Kategorie(name = name))
    }

    suspend fun addGeschäft(name: String) {
        geschäftDao.insert(Geschäft(name = name))
    }

    suspend fun deleteArtikel(artikelId: Int) {
        artikelDao.delete(artikelId)
    }

    suspend fun deleteKategorie(kategorieId: Int) {
        kategorieDao.delete(kategorieId)
    }

    suspend fun deleteGeschäft(geschäftId: Int) {
        geschäftDao.delete(geschäftId)
    }

    suspend fun assignGeschäftToArtikel(artikelId: Int, geschäftId: Int) {
        artikelDao.insertArtikelGeschäftCrossRef(ArtikelGeschäftCrossRef(artikelId, geschäftId))
    }
}
