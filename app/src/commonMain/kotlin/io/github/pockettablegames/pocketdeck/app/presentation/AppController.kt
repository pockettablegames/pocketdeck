package io.github.pockettablegames.pocketdeck.app.presentation

import io.github.pockettablegames.pocketdeck.api.game.GameAction
import io.github.pockettablegames.pocketdeck.api.game.GameConfig
import io.github.pockettablegames.pocketdeck.api.session.SessionState
import io.github.pockettablegames.pocketdeck.core.GameRegistry
import io.github.pockettablegames.pocketdeck.core.SessionManager
import io.github.pockettablegames.pocketdeck.games.cards.Card
import io.github.pockettablegames.pocketdeck.games.cards.CardsConfig
import io.github.pockettablegames.pocketdeck.games.cards.CardsGame
import io.github.pockettablegames.pocketdeck.games.cards.Suit
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