package com.example.testapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.Item
import com.example.testapp.data.ItemDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ItemViewModel(private val itemDao: ItemDao) : ViewModel() {
    val items: StateFlow<List<Item>> = itemDao.getAllItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    suspend fun addItem(name: String) {
        itemDao.insert(Item(name = name))
    }
}
