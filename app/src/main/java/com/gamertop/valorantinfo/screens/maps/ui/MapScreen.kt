package com.gamertop.valorantinfo.screens.maps.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gamertop.valorantinfo.api.service.MapResponse
import com.gamertop.valorantinfo.screens.Screen
import com.gamertop.valorantinfo.screens.maps.MapViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Previews
@Preview(name = "Map screen Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreen(navController = rememberNavController(), viewModel = MapViewModel(), uuid =  "default")
}

////////////////////////////////////////////////////////////////////////////////////////////////////

// Map screen toolbar
@Composable
fun MapScreenToolbar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = "Map",
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
                        navController.navigate(route = Screen.MapList.route) {
                            popUpTo(Screen.MapList.route) {
                                inclusive = true
                            }
                        }
                    }
            )
        }
    )
}

// Map screen body
fun mapSetup(coroutineScope: CoroutineScope, viewModel: MapViewModel, uuid: String) {
    coroutineScope.launch {
        viewModel.getMap(uuid = uuid)
    }
}

@Composable
fun MapScreenBody(viewModel: MapViewModel, uuid: String) {
    val isLoading: Boolean by viewModel.isMapLoading.observeAsState(initial = true)
    val map: MapResponse by viewModel.map.observeAsState(initial = MapResponse())
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        mapSetup(coroutineScope, viewModel, uuid)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn() {
                item {
                    Box(modifier = Modifier
                        .fillMaxWidth()) {
                        ConstraintLayout() {
                            val (img, box, text) = createRefs()
                            AsyncImage(
                                model = map.img,
                                contentDescription = map.name,
                                modifier = Modifier
                                    .constrainAs(img) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                    }
                                    .fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .constrainAs(box) {
                                        bottom.linkTo(img.bottom)
                                    }
                                    .background(color = Color(color = 0x854C3172))
                                    .fillMaxWidth()
                                    .height(54.dp)
                            )
                            Text(
                                text = map.name,
                                fontSize = 40.sp,
                                modifier = Modifier
                                    .constrainAs(text){
                                        bottom.linkTo(img.bottom)
                                        end.linkTo(box.end, margin = 50.dp)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Map screen view
@Composable
fun MapScreen(navController: NavHostController, viewModel: MapViewModel, uuid: String) {
    Scaffold(
        topBar = { MapScreenToolbar(navController = navController) },
        content = { MapScreenBody(viewModel = viewModel, uuid = uuid) }
    )
}
