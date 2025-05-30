package com.example.testapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.ui.theme.TestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("MainActivity: onCreate started")
        setContent {
            TestAppTheme {
                ShoppingListScreen(this)
            }
        }
    }
}

@Composable
fun ShoppingListScreen(context: Context) {
    println("ShoppingListScreen: Composable started")
    val prefs = context.getSharedPreferences("articles", Context.MODE_PRIVATE)
    var items by remember { mutableStateOf(prefs.getStringSet("items", emptySet())?.toList() ?: emptyList()) }
    var newItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Einkaufsliste",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("Neuer Artikel") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newItem.isNotBlank()) {
                        println("ShoppingListScreen: Adding item=$newItem")
                        items = items + newItem
                        prefs.edit().putStringSet("items", items.toSet()).apply()
                        newItem = ""
                    }
                }
            ) {
                Text("Hinzufügen")
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(items) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = {
                                println("ShoppingListScreen: Deleting item=$item")
                                items = items.filter { it != item }
                                prefs.edit().putStringSet("items", items.toSet()).apply()
                            }
                        ) {
                            Text("Löschen")
                        }
                    }
                }
            }
        }
    }
}
