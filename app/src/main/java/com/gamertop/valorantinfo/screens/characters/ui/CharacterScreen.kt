package com.gamertop.valorantinfo.screens.characters.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gamertop.valorantinfo.api.service.CharacterResponse
import com.gamertop.valorantinfo.screens.Screen
import com.gamertop.valorantinfo.screens.characters.CharacterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Character for preview
@Preview(name = "Character screen Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CharacterScreenPreview() {
    CharacterScreen(navController = rememberNavController(), viewModel = CharacterViewModel(), uuid =  "default")
}

////////////////////////////////////////////////////////////////////////////////////////////////////

// Character screen toolbar
@Composable
fun CharacterScreenToolbar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = "Valorant Character",
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
                        navController.navigate(route = Screen.CharacterList.route) {
                            popUpTo(Screen.CharacterList.route) {
                                inclusive = true
                            }
                        }
                    }
            )
        }
    )
}

// Character screen body
fun characterSetup(coroutineScope: CoroutineScope, viewModel: CharacterViewModel, uuid: String) {
    coroutineScope.launch {
        viewModel.getCharacter(uuid = uuid)
    }
}

@Composable
fun CharacterScreenBody(viewModel: CharacterViewModel, uuid: String) {
    val isLoading: Boolean by viewModel.isCharacterLoading.observeAsState(initial = true)
    val character: CharacterResponse by viewModel.character.observeAsState(initial = CharacterResponse())
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        characterSetup(coroutineScope, viewModel, uuid)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn() {
                item {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)) {
                        ConstraintLayout() {
                            val (img, box, text) = createRefs()
                            AsyncImage(
                                model = character.img,
                                contentDescription = character.name,
                                modifier = Modifier
                                    .constrainAs(img) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                    }
                                    .fillMaxHeight()
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
                                text = character.name,
                                fontSize = 40.sp,
                                modifier = Modifier
                                    .constrainAs(text){
                                        bottom.linkTo(img.bottom)
                                        end.linkTo(box.end, margin = 50.dp)
                                    }
                            )
                        }
                    }
                    Text(
                        text = character.description,
                        textAlign = TextAlign.Justify,
                        style = TextStyle(textDirection = TextDirection.Content),
                        modifier = Modifier.padding(all = 16.dp)
                    )
                    character.abilities.forEach{
                        Column(modifier = Modifier.padding(all = 16.dp)) {
                            Row {
                                AsyncImage(
                                    model = it.icon,
                                    contentDescription = it.name,
                                    modifier = Modifier
                                        .padding(all = 2.dp)
                                        .border(1.dp, MaterialTheme.colors.secondary)
                                        .background(color = Color.Gray)
                                )
                                Text(
                                    text = it.name,
                                    fontSize = 40.sp,
                                    textAlign = TextAlign.Justify,
                                    style = TextStyle(textDirection = TextDirection.Content),
                                    modifier = Modifier.padding(all = 2.dp)
                                )
                            }
                            Text(
                                text = it.description,
                                textAlign = TextAlign.Justify,
                                style = TextStyle(textDirection = TextDirection.Content),
                                modifier = Modifier.padding(all = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Character screen view
@Composable
fun CharacterScreen(navController: NavHostController, viewModel: CharacterViewModel, uuid: String) {
    Scaffold(
        topBar = { CharacterScreenToolbar(navController = navController) },
        content = { CharacterScreenBody(viewModel = viewModel, uuid = uuid) }
    )
}
