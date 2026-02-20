package com.meltingsource.playwithme.core

import com.meltingsource.playwithme.api.game.Game
import com.meltingsource.playwithme.api.game.GameAction
import com.meltingsource.playwithme.api.game.GameState

class GameRegistry {

    private val games =
        mutableMapOf<String, Game<out GameState, out GameAction>>()

    fun register(game: Game<out GameState, out GameAction>) {
        games[game.id] = game
    }

    fun get(gameId: String)
            : Game<out GameState, out GameAction>? =
        games[gameId]

    fun list(): List<Game<out GameState, out GameAction>> =
        games.values.toList()
}