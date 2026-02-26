package com.meltingsource.playwithme.app.presentation

import com.meltingsource.playwithme.api.game.GameAction
import com.meltingsource.playwithme.api.game.GameConfig
import com.meltingsource.playwithme.api.session.SessionState
import com.meltingsource.playwithme.core.GameRegistry
import com.meltingsource.playwithme.core.SessionManager
import com.meltingsource.playwithme.games.cards.Card
import com.meltingsource.playwithme.games.cards.CardsConfig
import com.meltingsource.playwithme.games.cards.CardsGame
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

        // Temporary bootstrap for MVP
        bootstrap()
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

    fun endGame() {
        sessionManager.endGame()
    }

    // ------------------------
    // Temporary bootstrap
    // ------------------------

    private fun bootstrap() {

        sessionManager.addPlayer("Alice")
        sessionManager.addPlayer("Gabriel")
        sessionManager.addPlayer("Vera")
//        sessionManager.addPlayer("Nuno")
//        sessionManager.addPlayer("Rita")
//        sessionManager.addPlayer("Ricardo")

        sessionManager.enterSetup("cards")

        sessionManager.selectConfig(
            CardsConfig(
                name = "Default",
                deck = (1..20).map {
                    Card(
                        id = it.toString(),
                        rank = it.toString(),
                        suit = "♠"
                    )
                },
                cardsPerPlayer = 3,
                showDeckZone = true,
                showDiscardZone = true,
                showPlayerTrickZone = true,
                stackCardsOnTable = false,
                refillDeckWithDiscard = false,
                groupByRank = false
            )
        )

        sessionManager.startGame()
    }
}