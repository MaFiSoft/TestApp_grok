package com.example.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun TestApp() {
    val navController = rememberNavController()
    var headerFooterColor by remember { mutableStateOf(Color.Blue) }
    var isMenuOpen by remember { mutableStateOf(false) }

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                navController = navController,
                headerFooterColor = headerFooterColor,
                isMenuOpen = isMenuOpen,
                onMenuToggle = { isMenuOpen = !isMenuOpen },
                onColorChange = { headerFooterColor = it }
            )
        }
        composable("articles") {
            ArticlesScreen(navController = navController, headerFooterColor = headerFooterColor)
        }
        composable("shops") {
            ShopsScreen(navController = navController, headerFooterColor = headerFooterColor)
        }
    }
}

@Composable
fun MainScreen(
    navController: NavController,
    headerFooterColor: Color,
    isMenuOpen: Boolean,
    onMenuToggle: () -> Unit,
    onColorChange: (Color) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar("Hauptmenü", headerFooterColor, showMenuIcon = true, onMenuClick = onMenuToggle)
        },
        bottomBar = {
            BottomBar(
                headerFooterColor = headerFooterColor,
                buttons = listOf(
                    "Artikel" to { navController.navigate("articles") },
                    "Geschäfte" to { navController.navigate("shops") }
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Test-\nApp",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 48.sp,
                color = Color.Black
            )
        }
        if (isMenuOpen) {
            ColorMenu(
                onColorSelect = {
                    onColorChange(it)
                    onMenuToggle()
                },
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun ArticlesScreen(navController: NavController, headerFooterColor: Color) {
    Scaffold(
        topBar = { TopBar("Artikel", headerFooterColor, showMenuIcon = false, onMenuClick = {}) },
        bottomBar = {
            BottomBar(
                headerFooterColor = headerFooterColor,
                buttons = listOf("Zurück" to { navController.popBackStack() })
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Artikel",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ShopsScreen(navController: NavController, headerFooterColor: Color) {
    Scaffold(
        topBar = { TopBar("Geschäfte", headerFooterColor, showMenuIcon = false, onMenuClick = {}) },
        bottomBar = {
            BottomBar(
                headerFooterColor = headerFooterColor,
                buttons = listOf("Zurück" to { navController.popBackStack() })
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Geschäfte",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun TopBar(title: String, color: Color, showMenuIcon: Boolean, onMenuClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .height(56.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (showMenuIcon) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Optionsmenü",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onMenuClick() }
            )
        }
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BottomBar(headerFooterColor: Color, buttons: List<Pair<String, () -> Unit>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(headerFooterColor)
            .height(56.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        buttons.forEach { (text, onClick) ->
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = text, color = Color.Black)
            }
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
                text = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onColorSelect(color) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(color)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = name, color = Color.Black)
                    }
                },
                onClick = { onColorSelect(color) }
            )
        }
    }
}
