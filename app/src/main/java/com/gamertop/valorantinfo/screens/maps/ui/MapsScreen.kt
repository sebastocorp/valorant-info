package com.gamertop.valorantinfo.screens.maps.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gamertop.valorantinfo.screens.Screen

@Preview(name = "Maps Layout Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MapsScreenPreview() {
    MapsScreen(
        navController = rememberNavController()
    )
}

@Composable
fun MapsScreen(navController: NavHostController) {

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Column (
            Modifier.clickable {
                navController.navigate(route = Screen.Home.route) {
                    popUpTo(Screen.Home.route) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text(text = "char 1")
            Text(text = "char 1")
            Text(text = "char 1")
            Text(text = "char 1")
        }
    }
}
