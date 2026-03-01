package com.meltingsource.playwithme.app.presentation

import com.meltingsource.playwithme.api.game.GameAction
import com.meltingsource.playwithme.api.game.GameConfig
import com.meltingsource.playwithme.api.session.SessionState
import com.meltingsource.playwithme.core.GameRegistry
import com.meltingsource.playwithme.core.SessionManager
import com.meltingsource.playwithme.games.cards.Card
import com.meltingsource.playwithme.games.cards.CardsConfig
import com.meltingsource.playwithme.games.cards.CardsGame
import com.meltingsource.playwithme.games.cards.Suit
import kotlinx.coroutines.flow.StateFlow

class AppController {

    private val sessionManager: SessionManager

    init {

        val registry = GameRegistry(
            games = listOf(
                CardsGame()
            )
        )

        sessionManager = SessionManager(
            sessionId = "local-session",
            registry = registry
        )
    }

    val sessionState: StateFlow<SessionState> = sessionManager.state

    // ------------------------
    // Public API for UI
    // ------------------------

    fun apply(action: GameAction) {
        sessionManager.apply(action)
    }

    fun addPlayer(name: String) {
        sessionManager.addPlayer(name)
    }

    fun removePlayer(id: String) {
        sessionManager.removePlayer(id)
    }

    fun switchPlayer() {
        sessionManager.switchToNextPlayer()
    }

    fun enterSetup(gameId: String) {
        sessionManager.enterSetup(gameId)
    }

    fun selectConfig(config: GameConfig) {
        sessionManager.selectConfig(config)
    }

    fun startGame() {
        sessionManager.startGame()
    }

    fun enterScore() {
        sessionManager.enterScore()
    }

    fun updateScore(playerId: String, score: Int) {
        sessionManager.updateScore(playerId, score)
    }

    fun enterResults() {
        sessionManager.enterResults()
    }

    fun endGame() {
        sessionManager.endGame()
    }

    fun playAgain() {
        sessionManager.playAgain()
    }
}