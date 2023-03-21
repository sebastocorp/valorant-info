package com.gamertop.valorantinfo.screens.agents.ui

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
import com.gamertop.valorantinfo.api.service.AgentListItemResponse
import com.gamertop.valorantinfo.screens.Screen
import com.gamertop.valorantinfo.screens.agents.AgentListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


// Previews
@Preview(name = "Agents view Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AgentListScreenPreview() {
    AgentListScreen(navController = rememberNavController(), AgentListViewModel())
}

@Preview(name = "CLRow view Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ALRowPreview() {
    ALRow(
        character = AgentListItemResponse(
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

// Agent list screen toolbar
@Composable
fun AgentListScreenToolbar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = "Agent List",
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

// Agent list screen Body
fun agentListSetup(coroutineScope: CoroutineScope, viewModel: AgentListViewModel) {
    coroutineScope.launch {
        viewModel.getAgentList()
    }
}

@Composable
fun ALRow(character: AgentListItemResponse, action: () -> Unit) {
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

@Composable
fun AgentListScreenBody(navController: NavHostController, viewModel: AgentListViewModel) {
    val isLoading: Boolean by viewModel.isAgentListLoading.observeAsState(initial = true)
    val agentList: List<AgentListItemResponse> by viewModel.agentList.observeAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        agentListSetup(coroutineScope, viewModel)
    } else {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            LazyColumn () {
                agentList.forEach{
                    item {
                        ALRow(it, action = {
                            navController.navigate(route = Screen.Agent.passUuid(it.uuid)) {
                                popUpTo(Screen.Agent.route) {
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

// Agent List screen
@Composable
fun AgentListScreen(navController: NavHostController, viewModel: AgentListViewModel) {
    Scaffold(
        topBar = { AgentListScreenToolbar(navController = navController) },
        content = { AgentListScreenBody(navController = navController, viewModel) }
    )
}