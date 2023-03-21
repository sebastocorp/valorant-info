package com.gamertop.valorantinfo.screens.agents.ui

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
import com.gamertop.valorantinfo.api.service.AgentResponse
import com.gamertop.valorantinfo.screens.Screen
import com.gamertop.valorantinfo.screens.agents.AgentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Previews
@Preview(name = "Agent screen Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AgentScreenPreview() {
    AgentScreen(navController = rememberNavController(), viewModel = AgentViewModel(), uuid =  "default")
}

////////////////////////////////////////////////////////////////////////////////////////////////////

// Agent screen toolbar
@Composable
fun AgentScreenToolbar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = "Valorant Agent",
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
                        navController.navigate(route = Screen.AgentList.route) {
                            popUpTo(Screen.AgentList.route) {
                                inclusive = true
                            }
                        }
                    }
            )
        }
    )
}

// Agent screen body
fun agentSetup(coroutineScope: CoroutineScope, viewModel: AgentViewModel, uuid: String) {
    coroutineScope.launch {
        viewModel.getAgent(uuid = uuid)
    }
}

@Composable
fun AgentScreenBody(viewModel: AgentViewModel, uuid: String) {
    val isLoading: Boolean by viewModel.isAgentLoading.observeAsState(initial = true)
    val agent: AgentResponse by viewModel.agent.observeAsState(initial = AgentResponse())
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        agentSetup(coroutineScope, viewModel, uuid)
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
                                model = agent.img,
                                contentDescription = agent.name,
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
                                text = agent.name,
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
                        text = agent.description,
                        textAlign = TextAlign.Justify,
                        style = TextStyle(textDirection = TextDirection.Content),
                        modifier = Modifier.padding(all = 16.dp)
                    )
                    agent.abilities.forEach{
                        Column(modifier = Modifier.padding(all = 16.dp)) {
                            Row {
                                AsyncImage(
                                    model = it.icon,
                                    contentDescription = it.name,
                                    modifier = Modifier
                                        .padding(all = 2.dp)
                                        .border(1.dp, MaterialTheme.colors.secondary)
                                        .background(color = Color.Gray)
                                        .size(60.dp)
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

// Agent screen view
@Composable
fun AgentScreen(navController: NavHostController, viewModel: AgentViewModel, uuid: String) {
    Scaffold(
        topBar = { AgentScreenToolbar(navController = navController) },
        content = { AgentScreenBody(viewModel = viewModel, uuid = uuid) }
    )
}
