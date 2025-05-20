// Stand: 2025-05-20_15:59
package com.example.testapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListScreen()
        }
    }
}

@Composable
fun ShoppingListScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val sharedPreferences = context.getSharedPreferences("shopping_list", Context.MODE_PRIVATE)
    var items by remember { mutableStateOf(loadItems(sharedPreferences)) }
    var newItem by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Einkaufsliste", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            TextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("Neuer Artikel") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newItem.isNotBlank()) {
                    items = items + newItem
                    saveItems(sharedPreferences, items)
                    newItem = ""
                    println("Adding item: $newItem")
                }
            }) {
                Text("Hinzufügen")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(text = item, modifier = Modifier.weight(1f))
                    Button(onClick = {
                        items = items.filter { it != item }
                        saveItems(sharedPreferences, items)
                        println("Deleting item: $item")
                    }) {
                        Text("Löschen")
                    }
                }
            }
        }
    }
}

private fun loadItems(sharedPreferences: android.content.SharedPreferences): List<String> {
    val items = sharedPreferences.getString("items", "") ?: ""
    return if (items.isEmpty()) emptyList() else items.split(",")
}

private fun saveItems(sharedPreferences: android.content.SharedPreferences, items: List<String>) {
    sharedPreferences.edit().putString("items", items.joinToString(",")).apply()
}
