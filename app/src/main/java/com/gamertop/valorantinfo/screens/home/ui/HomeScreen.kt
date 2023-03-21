package com.gamertop.valorantinfo.screens.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gamertop.valorantinfo.screens.Screen

// Only for preview
@Preview(name = "Home view Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}

////////////////////////////////////////////////////////////////////////////////////////////////////

class HSRowImg(var url: String = "", val size: Dp, val padding: Dp, val borderSize: Dp)
class HSRowTextPadding(val top: Dp, val bottom: Dp, val start: Dp, val end: Dp)
class HSRowText(var text: String = "", val fontSize: TextUnit, val padding: HSRowTextPadding)
class HSRow(val padding: Dp, val img: HSRowImg, val text: HSRowText )

// Home screen toolbar
@Composable
fun HomeScreenToolbar() {
    TopAppBar(
        title = { Text(text = "Valorant Info") }
    )
}

// Home screen body
@Composable
fun HSRow(row: HSRow, action: () -> Unit ) {
    Row(modifier = Modifier
        .padding(row.padding)
        .clickable { action() }) {
        AsyncImage(
            model = row.img.url,
            contentDescription = "Translated description of what the image contains",
            modifier = Modifier
                .size(size = row.img.size)
                .padding(all = row.img.padding)
                .border(row.img.borderSize, MaterialTheme.colors.secondary, CircleShape)
                .background(color = Color.Gray, shape = CircleShape)
                .clip(shape = CircleShape)
        )
        Text(
            text = row.text.text,
            textAlign = TextAlign.Left,
            fontSize = row.text.fontSize,
            modifier = Modifier
                .padding(
                    top = row.text.padding.top,
                    bottom = row.text.padding.bottom,
                    start = row.text.padding.start,
                    end = row.text.padding.end,
                )
                .fillMaxWidth()
        )
    }
}

@Composable
fun HomeScreenBody(navController: NavHostController) {
    val row = HSRow(
        padding = 2.dp,
        HSRowImg(
            size = 44.dp,
            padding = 2.dp,
            borderSize = 1.dp
        ),
        HSRowText(
            fontSize = 30.sp,
            padding = HSRowTextPadding(
                top = 2.dp,
                bottom = 2.dp,
                start = 16.dp,
                end = 2.dp
            )
        )
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Column() {
            row.img.url = "https://cdn.onlinewebfonts.com/svg/img_24710.png"
            row.text.text = "Personajes"
            HSRow(row = row, action = {
                navController.navigate(route = Screen.CharacterList.route)
            })
            row.img.url = "https://cdn.onlinewebfonts.com/svg/img_24710.png"
            row.text.text = "Agentes"
            HSRow(row = row, action = {
                navController.navigate(route = Screen.AgentList.route)
            })
            row.img.url = "https://cdn.onlinewebfonts.com/svg/img_465777.png"
            row.text.text = "Mapas"
            HSRow(row = row, action = {
                navController.navigate(route = Screen.MapList.route)
            })
        }
    }
}

// Home screen view
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { HomeScreenToolbar() },
        content = { HomeScreenBody(navController = navController) }
    )
}
