package com.example.testapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.extended.Default
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

@Composable
fun TopBar(title: String, color: Color, showMenuIcon: Boolean, onMenuClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .height(56.dp)
            .padding(horizontal = 16.dp)
    ) {
        if (showMenuIcon) {
            Icon(
                imageVector = Icons.Default.Menu,
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

@Composable
fun BottomBar(color: Color, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .height(56.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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
fun ColorMenu(onColorSelect: (Color) -> Unit, modifier: Modifier = Modifier) {
    val colors = listOf(
        Color.Blue to "Blau",
        Color.Red to "Rot",
        Color.Green to "Grün",
        Color.Yellow to "Gelb",
        Color.Magenta to "Lila"
    )
    DropdownMenu(
        expanded = true,
        onDismissRequest = {},
        modifier = modifier
            .width(200.dp)
            .background(Color.White)
    ) {
        colors.forEach { (color, name) ->
            DropdownMenuItem(
                text = { Text(name, color = Color.Black) },
                onClick = { onColorSelect(color) },
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(color)
                    )
                }
            )
        }
    }
}

@Composable
fun MainScreen(navController: NavController, selectedColor: Color, onColorSelect: (Color) -> Unit) {
    var showMenu by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar(
            title = "Test-App",
            color = selectedColor,
            showMenuIcon = true,
            onMenuClick = { showMenu = true }
        )
        if (showMenu) {
            ColorMenu(
                onColorSelect = { color ->
                    onColorSelect(color)
                    showMenu = false
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BottomBar(color = selectedColor, navController = navController)
    }
}

@Composable
fun ArticleScreen(navController: NavController, selectedColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar(
            title = "Artikel",
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
