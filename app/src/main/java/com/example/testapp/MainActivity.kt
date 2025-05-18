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
import com.example.testapp.ui.theme.TestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                TestApp()
            }
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
                modifier = Modifier.clickable { navController.navigate("articles") }
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
fun ColorMenu(expanded: Boolean, onDismiss: () -> Unit, onColorSelect: (Color) -> Unit, modifier: Modifier = Modifier) {
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
        modifier = modifier
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

@Composable
fun MainScreen(navController: NavController, selectedColor: Color, onColorSelect: (Color) -> Unit) {
    var showMenu by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Test-App", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = selectedColor
                ),
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
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
                    onColorSelect = { color ->
                        onColorSelect(color)
                        showMenu = false
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ArticleScreen(navController: NavController, selectedColor: Color) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Artikel", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = selectedColor
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Zurück",
                            tint = Color.White
                        )
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                items(listOf("Milch", "Brot", "Eier", "Käse", "Äpfel")) { item ->
                    Text(
                        text = item,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StoreScreen(navController: NavController, selectedColor: Color) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Geschäfte", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = selectedColor
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Zurück",
                            tint = Color.White
                        )
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
}

@Composable
fun TestApp() {
    val navController = rememberNavController()
    var selectedColor by remember { mutableStateOf(Color.Blue) }

    MaterialTheme {
        NavHost(navController, startDestination = "main") {
            composable("main") {
                MainScreen(
                    navController = navController,
                    selectedColor = selectedColor,
                    onColorSelect = { selectedColor = it }
                )
            }
            composable("articles") {
                ArticleScreen(navController = navController, selectedColor = selectedColor)
            }
            composable("stores") {
                StoreScreen(navController = navController, selectedColor = selectedColor)
            }
        }
    }
}
