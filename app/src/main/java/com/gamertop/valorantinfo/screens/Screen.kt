package com.gamertop.valorantinfo.screens

const val CHARACTER_ARGUMENT_KEY = "uuid"

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
    object Agents: Screen(route = "agents_screen")
    object Maps: Screen(route = "maps_screen")
}
