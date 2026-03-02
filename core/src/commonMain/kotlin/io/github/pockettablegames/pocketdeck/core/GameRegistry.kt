package io.github.pockettablegames.pocketdeck.core

import io.github.pockettablegames.pocketdeck.api.game.Game

class GameRegistry(
    private val games: List<Game<*, *, *>>
) {
    fun get(id: String): Game<*, *, *>? = games.firstOrNull { it.id == id }
    fun all(): List<Game<*, *, *>> = games
}