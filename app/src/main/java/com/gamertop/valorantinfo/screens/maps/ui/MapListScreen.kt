package com.gamertop.valorantinfo.screens.maps.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gamertop.valorantinfo.api.service.MapListItemResponse
import com.gamertop.valorantinfo.screens.Screen
import com.gamertop.valorantinfo.screens.maps.MapListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


// Previews
@Preview(name = "Maps view Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MapListScreenPreview() {
    MapListScreen(navController = rememberNavController(), MapListViewModel())
}

@Preview(name = "MLRow view Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MLRowPreview() {
    MLRow(
        map = MapListItemResponse(
            name = "default",
            uuid = "uuid",
            icon = "url"
        ),
        action = {}
    )
}

////////////////////////////////////////////////////////////////////////////////////////////////////

// Map list screen toolbar
@Composable
fun MapListScreenToolbar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = "Map List",
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) },
        navigationIcon = {
            Text(
                text = "back",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(route = Screen.Home.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    }
            )
        }
    )
}

// Map list screen Body
fun mapListSetup(coroutineScope: CoroutineScope, viewModel: MapListViewModel) {
    coroutineScope.launch {
        viewModel.getMapList()
    }
}

@Composable
fun MLRow(map: MapListItemResponse, action: () -> Unit) {
    val padding = 2.dp
    val imgSize = 70.dp
    val imgPadding = 2.dp
    val imgBorderSize = 1.dp
    val nameSize = 46.sp
    val namePaddingTop = 2.dp
    val namePaddingBottom = 2.dp
    val namePaddingStart = 16.dp
    val namePaddingEnd = 2.dp

    Row(modifier = Modifier
        .padding(padding)
        .clickable { action() }) {
        AsyncImage(
            model = map.icon,
            contentDescription = map.name,
            modifier = Modifier
                .size(size = imgSize)
                .padding(all = imgPadding)
                .border(imgBorderSize, MaterialTheme.colors.secondary, CircleShape)
                .background(color = Color.White, shape = CircleShape)
                .clip(shape = CircleShape)
        )
        Text(
            text = map.name,
            textAlign = TextAlign.Left,
            fontSize = nameSize,
            modifier = Modifier
                .padding(
                    top = namePaddingTop,
                    bottom = namePaddingBottom,
                    start = namePaddingStart,
                    end = namePaddingEnd,
                )
                .fillMaxWidth()
        )
    }
}

@Composable
fun MapListScreenBody(navController: NavHostController, viewModel: MapListViewModel) {
    val isLoading: Boolean by viewModel.isMapListLoading.observeAsState(initial = true)
    val mapList: List<MapListItemResponse> by viewModel.mapList.observeAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        mapListSetup(coroutineScope, viewModel)
    } else {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            LazyColumn () {
                mapList.forEach{
                    item {
                        MLRow(it, action = {
                            navController.navigate(route = Screen.Map.passUuid(it.uuid)) {
                                popUpTo(Screen.Map.route) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}

// Map List screen
@Composable
fun MapListScreen(navController: NavHostController, viewModel: MapListViewModel) {
    Scaffold(
        topBar = { MapListScreenToolbar(navController = navController) },
        content = { MapListScreenBody(navController = navController, viewModel) }
    )
}