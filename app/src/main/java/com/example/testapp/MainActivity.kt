package com.example.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.testapp.data.AppDatabase
import com.example.testapp.data.Article
import com.example.testapp.ui.theme.TestAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()
        setContent {
            TestAppTheme {
                TestApp(db)
            }
        }
    }
}

@Composable
fun TopBar(title: String, color: Color, showMenuIcon: Boolean, onMenuClick: () -> Unit) {
    Surface(
        color = color,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            if (showMenuIcon) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                        .clickable { onMenuClick() }
                )
            }
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun BottomBar(color: Color, navController: NavController) {
    Surface(
        color = color,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Artikel",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.clickable { navController.navigate("edit_articles") }
            )
            Text(
                text = "Geschäfte",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.clickable { navController.navigate("stores") }
            )
        }
    }
}

@Composable
fun ColorMenu(expanded: Boolean, onDismiss: () -> Unit, onColorSelect: (Color) -> Unit) {
    val colors = listOf(
        Color.Blue to "Blau",
        Color.Red to "Rot",
        Color.Green to "Grün",
        Color.Yellow to "Gelb",
        Color.Magenta to "Lila"
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .width(200.dp)
            .background(Color.White)
    ) {
        colors.forEach { (color, name) ->
            DropdownMenuItem(
                text = { Text(name, color = Color.Black) },
                onClick = {
                    onColorSelect(color)
                    onDismiss()
                },
                modifier = Modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(navController: NavController, selectedColor: Color, db: AppDatabase) {
    val scope = rememberCoroutineScope()
    var articles by remember { mutableStateOf(listOf<Article>()) }
    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        db.articleDao().getAllArticles().collectLatest { articles = it }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Einkaufsliste", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = selectedColor),
                actions = {
                    IconButton(onClick = { navController.navigate("edit_articles") }) {
                        Icon(Icons.Filled.Add, contentDescription = "Artikel hinzufügen", tint = Color.White)
                    }
                }
            )
        },
        bottomBar = { BottomBar(color = selectedColor, navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            if (showMenu) {
                ColorMenu(
                    expanded = showMenu,
                    onDismiss = { showMenu = false },
                    onColorSelect = { showMenu = false }
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                items(articles) { article ->
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
                            Checkbox(
                                checked = false,
                                onCheckedChange = {},
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Column {
                                Text(
                                    text = article.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "${article.category ?: "Keine Kategorie"} | ${article.stores ?: "Keine Geschäfte"}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditArticlesScreen(navController: NavController, selectedColor: Color, db: AppDatabase) {
    val scope = rememberCoroutineScope()
    var articles by remember { mutableStateOf(listOf<Article>()) }
    var newArticleName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        db.articleDao().getAllArticles().collectLatest { articles = it }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Artikel bearbeiten", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = selectedColor),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Zurück", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newArticleName,
                    onValueChange = { newArticleName = it },
                    label = { Text("Neuer Artikel") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (newArticleName.isNotBlank()) {
                            scope.launch {
                                db.articleDao().insert(Article(name = newArticleName))
                                newArticleName = ""
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
                    .padding(horizontal = 16.dp)
            ) {
                items(articles) { article ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = article.name,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = {
                                scope.launch {
                                    db.articleDao().delete(article)
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

@Composable
fun StoreScreen(navController: NavController, selectedColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar(
            title = "Geschäfte",
            color = selectedColor,
            showMenuIcon = false,
            onMenuClick = {}
        )
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Zurück")
        }
    }
}

@Composable
fun TestApp(db: AppDatabase) {
    val navController = rememberNavController()
    var selectedColor by remember { mutableStateOf(Color.Blue) }

    MaterialTheme {
        NavHost(navController, startDestination = "shopping_list") {
            composable("shopping_list") {
                ShoppingListScreen(navController, selectedColor, db)
            }
            composable("edit_articles") {
                EditArticlesScreen(navController, selectedColor, db)
            }
            composable("stores") {
                StoreScreen(navController, selectedColor)
            }
        }
    }
}
