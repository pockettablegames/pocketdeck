package com.meltingsource.playwithme.core

import com.meltingsource.playwithme.api.Player
import com.meltingsource.playwithme.api.game.Game
import com.meltingsource.playwithme.api.game.GameAction
import com.meltingsource.playwithme.api.game.GameState

class GameCoordinator(
    private val registry: GameRegistry
) {

    private var engine: GameEngine<out GameState, out GameAction>? = null
    private var activeGameId: String? = null

    fun startGame(
        gameId: String,
        players: List<Player>
    ) {
        val game = registry.get(gameId)
            ?: error("Game not found: $gameId")

        @Suppress("UNCHECKED_CAST")
        engine = GameEngine(
            game as Game<GameState, GameAction>,
            players
        )

        activeGameId = gameId
    }

    fun getGameState(): GameState? =
        engine?.getState()
}
