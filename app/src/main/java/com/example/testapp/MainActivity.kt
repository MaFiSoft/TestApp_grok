package com.example.testapp

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
import androidx.room.Room
import com.example.testapp.data.AppDatabase
import com.example.testapp.data.Article
import com.example.testapp.ui.theme.TestAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("MainActivity: onCreate started")
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).fallbackToDestructiveMigration().build()
        println("MainActivity: Database initialized")
        setContent {
            TestAppTheme {
                ShoppingListScreen(db)
            }
        }
    }
}

@Composable
fun ShoppingListScreen(db: AppDatabase) {
    println("ShoppingListScreen: Composable started")
    val scope = rememberCoroutineScope()
    var items by remember { mutableStateOf(listOf<Article>()) }
    var newItem by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        println("ShoppingListScreen: LaunchedEffect started")
        db.articleDao().getAllArticles().collect { articles ->
            println("ShoppingListScreen: Articles collected, size=${articles.size}")
            items = articles
        }
    }

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
            modifier = Modifier.padding(bottom = 16.dp)
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
                        scope.launch {
                            println("ShoppingListScreen: Inserting article=$newItem")
                            db.articleDao().insert(Article(name = newItem))
                            newItem = ""
                        }
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
                            text = item.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = {
                                scope.launch {
                                    println("ShoppingListScreen: Deleting article=${item.name}")
                                    db.articleDao().delete(item)
                                }
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
