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
import com.example.testapp.data.AppDatenbank
import com.example.testapp.ui.ArtikelViewModel
import com.example.testapp.ui.theme.TestAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("MainActivity: onCreate started")
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatenbank::class.java,
            "app-datenbank"
        )
            .fallbackToDestructiveMigration()
            .build()
        val artikelViewModel = ArtikelViewModel(db.artikelDao(), db.kategorieDao(), db.geschaeftDao())
        setContent {
            TestAppTheme {
                GesamtlisteMenue(artikelViewModel)
            }
        }
    }
}

@Composable
fun GesamtlisteMenue(viewModel: ArtikelViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var newArtikel by remember { mutableStateOf("") }
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
                value = newArtikel,
                onValueChange = { newArtikel = it },
                label = { Text("Neuer Artikel") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newArtikel.isNotBlank()) {
                        println("GesamtlisteMenue: Adding artikel=$newArtikel")
                        coroutineScope.launch {
                            viewModel.addArtikel(newArtikel)
                            newArtikel = ""
                        }
                    }
                }
            ) {
                Text("Hinzufuegen")
            }
        }
        val artikelList by viewModel.artikel.collectAsState()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(artikelList) { artikel ->
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
                            text = artikel.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = {
                                println("GesamtlisteMenue: Deleting artikel=${artikel.name}")
                                coroutineScope.launch {
                                    viewModel.deleteArtikel(artikel.id)
                                }
                            }
                        ) {
                            Text("Loeschen")
                        }
                    }
                }
            }
        }
    }
}
