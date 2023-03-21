package com.gamertop.valorantinfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gamertop.valorantinfo.screens.AGENT_ARGUMENT_KEY
import com.gamertop.valorantinfo.screens.CHARACTER_ARGUMENT_KEY
import com.gamertop.valorantinfo.screens.Screen
import com.gamertop.valorantinfo.screens.agents.AgentListViewModel
import com.gamertop.valorantinfo.screens.agents.AgentViewModel
import com.gamertop.valorantinfo.screens.agents.ui.AgentListScreen
import com.gamertop.valorantinfo.screens.agents.ui.AgentScreen
import com.gamertop.valorantinfo.screens.characters.CharacterListViewModel
import com.gamertop.valorantinfo.screens.characters.CharacterViewModel
import com.gamertop.valorantinfo.screens.characters.ui.CharacterListScreen
import com.gamertop.valorantinfo.screens.characters.ui.CharacterScreen
import com.gamertop.valorantinfo.screens.home.ui.HomeScreen
import com.gamertop.valorantinfo.screens.maps.ui.MapsScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route
        ){
            HomeScreen(navController)
        }
        // character List screen
        composable(
            route = Screen.CharacterList.route
        ){
            CharacterListScreen(navController, CharacterListViewModel())
        }
        // Single character screen
        composable(
            route = Screen.Character.route,
            arguments = listOf(navArgument(CHARACTER_ARGUMENT_KEY){
                type = NavType.StringType
            })
        ){
            CharacterScreen(navController, CharacterViewModel(), it.arguments?.getString(CHARACTER_ARGUMENT_KEY).toString())
        }
        // Agent list screen
        composable(
            route = Screen.AgentList.route
        ){
            AgentListScreen(navController, AgentListViewModel())
        }
        // Single agent screen
        composable(
            route = Screen.Agent.route,
            arguments = listOf(navArgument(AGENT_ARGUMENT_KEY){
                type = NavType.StringType
            })
        ){
            AgentScreen(navController, AgentViewModel(), it.arguments?.getString(AGENT_ARGUMENT_KEY).toString())
        }
        // Map list screen
        composable(
            route = Screen.Maps.route
        ){
            MapsScreen(navController)
        }
    }
}
