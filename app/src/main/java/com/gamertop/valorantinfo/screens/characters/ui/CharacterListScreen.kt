package com.gamertop.valorantinfo.screens.characters.ui

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gamertop.valorantinfo.api.service.CharacterListItemResponse
import com.gamertop.valorantinfo.screens.Screen
import com.gamertop.valorantinfo.screens.characters.CharacterListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


// Characters for preview
@Preview(name = "Characters view Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CharacterListScreenPreview() {
    CharacterListScreen(navController = rememberNavController(), CharacterListViewModel())
}

@Preview(name = "CLRow view Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CLRowPreview() {
    CLRow(
        character = CharacterListItemResponse(
            name = "default",
            uuid = "uuid",
            description = "desc",
            icon = "url",
            isPlayable = true
        ),
        action = {}
    )
}

////////////////////////////////////////////////////////////////////////////////////////////////////

// Character list screen toolbar
@Composable
fun CharacterListScreenToolbar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = "Character List",
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

// Character list screen Body
fun characterListSetup(coroutineScope: CoroutineScope, viewModel: CharacterListViewModel) {
    coroutineScope.launch {
        viewModel.getCharacterList()
    }
}

@Composable
fun CLRow(character: CharacterListItemResponse, action: () -> Unit) {
    val padding = 2.dp
    val imgSize = 70.dp
    val imgPadding = 2.dp
    val imgBorderSize = 1.dp
    val nameSize = 30.sp
    val namePaddingTop = 2.dp
    val namePaddingBottom = 2.dp
    val namePaddingStart = 16.dp
    val namePaddingEnd = 2.dp
    val descSize = 15.sp
    val descPaddingTop = 2.dp
    val descPaddingBottom = 2.dp
    val descPaddingStart = 16.dp
    val descPaddingEnd = 2.dp

    if (character.isPlayable) {
        Row(modifier = Modifier
            .padding(padding)
            .clickable { action() }) {
            AsyncImage(
                model = character.icon,
                contentDescription = character.name,
                modifier = Modifier
                    .size(size = imgSize)
                    .padding(all = imgPadding)
                    .border(imgBorderSize, MaterialTheme.colors.secondary, CircleShape)
                    .background(color = Color.Gray, shape = CircleShape)
                    .clip(shape = CircleShape)
            )
            Column() {
                Text(
                    text = character.name,
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
                Text(
                    text = character.description,
                    textAlign = TextAlign.Left,
                    fontSize = descSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(
                            top = descPaddingTop,
                            bottom = descPaddingBottom,
                            start = descPaddingStart,
                            end = descPaddingEnd,
                        )
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CharacterListScreenBody(navController: NavHostController, viewModel: CharacterListViewModel) {
    val isLoading: Boolean by viewModel.isCharacterListLoading.observeAsState(initial = true)
    val characterList: List<CharacterListItemResponse> by viewModel.characterList.observeAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        characterListSetup(coroutineScope, viewModel)
    } else {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            LazyColumn () {
                characterList.forEach{
                    item {
                        CLRow(it, action = {
                            navController.navigate(route = Screen.Character.passUuid(it.uuid)) {
                                popUpTo(Screen.Character.route) {
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

// Character List screen
@Composable
fun CharacterListScreen(navController: NavHostController, viewModel: CharacterListViewModel) {
    Scaffold(
        topBar = { CharacterListScreenToolbar(navController = navController) },
        content = { CharacterListScreenBody(navController = navController, viewModel) }
    )
}
