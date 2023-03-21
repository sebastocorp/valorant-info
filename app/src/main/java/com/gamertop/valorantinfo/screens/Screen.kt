package com.gamertop.valorantinfo.screens

const val CHARACTER_ARGUMENT_KEY = "uuid"
const val AGENT_ARGUMENT_KEY = "uuid"
const val MAP_ARGUMENT_KEY = "uuid"

sealed class Screen (val route: String) {
    object Home: Screen(route = "home_screen")
    object CharacterList: Screen(route = "character_list_screen")
    object Character: Screen(route = "character_screen/{$CHARACTER_ARGUMENT_KEY}") {
        fun passUuid(uuid: String): String {
            return this.route.replace(
                oldValue = "{$CHARACTER_ARGUMENT_KEY}",
                newValue = uuid
            )
        }
    }
    object AgentList: Screen(route = "agent_list_screen")
    object Agent: Screen(route = "agent_screen/{$AGENT_ARGUMENT_KEY}") {
        fun passUuid(uuid: String): String {
            return this.route.replace(
                oldValue = "{$AGENT_ARGUMENT_KEY}",
                newValue = uuid
            )
        }
    }
    object MapList: Screen(route = "map_list_screen")
    object Map: Screen(route = "map_screen/{$MAP_ARGUMENT_KEY}") {
        fun passUuid(uuid: String): String {
            return this.route.replace(
                oldValue = "{$MAP_ARGUMENT_KEY}",
                newValue = uuid
            )
        }
    }
}
