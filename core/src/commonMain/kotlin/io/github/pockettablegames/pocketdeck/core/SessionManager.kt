package io.github.pockettablegames.pocketdeck.core

import io.github.pockettablegames.pocketdeck.api.Player
import io.github.pockettablegames.pocketdeck.api.game.Game
import io.github.pockettablegames.pocketdeck.api.game.GameAction
import io.github.pockettablegames.pocketdeck.api.game.GameConfig
import io.github.pockettablegames.pocketdeck.api.game.GameState
import io.github.pockettablegames.pocketdeck.api.session.SessionPhase
import io.github.pockettablegames.pocketdeck.api.session.SessionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SessionManager(
    sessionId: String,
    private val registry: GameRegistry
) {
    private val _state = MutableStateFlow(SessionState(sessionId))

    val state: StateFlow<SessionState> = _state.asStateFlow()

    private var game: Game<GameState, GameAction, GameConfig>? = null

    // ------------------------
    // Lobby
    // ------------------------

    @OptIn(ExperimentalUuidApi::class)
    fun addPlayer(name: String) {
        _state.update {
            if (it.phase == SessionPhase.LOBBY) {
                val player = Player(
                    id = Uuid.random().toString(),
                    name = name,
                    avatar = it.players.size
                )

                val players = it.players + player

                it.copy(
                    players = players,
                    activePlayerId = players.firstOrNull()?.id
                )
            } else {
                it
            }
        }
    }

    fun removePlayer(id: String) {
        _state.update { lastState ->
            if (lastState.phase == SessionPhase.LOBBY) {
                val players = lastState.players.filter { it.id != id }

                lastState.copy(
                    players = players,
                    activePlayerId = players.firstOrNull()?.id
                )
            } else {
                lastState
            }
        }
    }

    // ------------------------
    // Setup
    // ------------------------

    fun enterSetup(gameId: String) {
        _state.update {
            it.copy(
                phase = SessionPhase.SETUP,
                selectedGameId = gameId
            )
        }
    }

    fun selectConfig(config: GameConfig) {
        _state.update {
            it.copy(
                selectedConfig = config
            )
        }
    }

    // ------------------------
    // Start Game
    // ------------------------

    fun startGame() {
        _state.update { currentState ->
            currentState.selectedGameId?.let { gameId ->
                currentState.selectedConfig?.let { config ->

                    registry.get(gameId)?.let { newGame ->
                        game = newGame as Game<GameState, GameAction, GameConfig>

                        currentState.copy(
                            phase = SessionPhase.PLAYING,
                            gameState = newGame.initialState(currentState.players, config, null)
                        )
                    }
                }
            } ?: currentState
        }
    }

    // ------------------------
    // Runtime
    // ------------------------

    fun apply(action: GameAction) {
        _state.update { currentState ->
            currentState.activePlayerId?.let { actorId ->
                currentState.players.firstOrNull { it.id == actorId }?.let { actor ->

                    currentState.selectedConfig?.let { config ->
                        currentState.gameState?.let { gameState ->
                            game?.apply(gameState, action, actor, config)?.let { newGameState ->
                                currentState.copy(
                                    gameState = newGameState
                                )
                            }
                        }
                    }
                }
            } ?: currentState
        }
    }

    fun enterSwitchPlayer() {
        _state.update {
            it.copy(
                phase = SessionPhase.SWITCH_PLAYER
            )
        }
    }

    fun returnToGame() {
        _state.update {
            it.copy(
                phase = SessionPhase.PLAYING
            )
        }
    }

    fun switchToNextPlayer() {
        _state.update { currentState ->
            val players = currentState.players
            val current = currentState.activePlayerId

            val index = players.indexOfFirst { it.id == current }

            val next = if (index != -1) {
                players[(index + 1) % players.size]
            } else {
                null
            }

            next?.let {
                currentState.copy(
                    activePlayerId = next.id
                )
            } ?: currentState
        }
    }

    fun switchToPlayer(playerId: String) {
        _state.update { currentState ->
            val players = currentState.players

            if (playerId != currentState.activePlayerId) {
                val next = players.firstOrNull { it.id == playerId }

                next?.let {
                    currentState.copy(
                        activePlayerId = next.id
                    )
                } ?: currentState
            } else {
                currentState
            }
        }
    }

    fun enterScore() {
        _state.update {
            it.copy(
                phase = SessionPhase.SCORE
            )
        }
    }

    fun updateScore(playerId: String, score: Int) {
        _state.update { state ->
            state.copy(
                results = state.results.toMutableMap().also {
                    it[playerId] = score
                }
            )
        }
    }

    fun enterResults() {
        _state.update {
            it.copy(
                phase = SessionPhase.RESULTS
            )
        }
    }

    fun endGame() {
        _state.update {
            game = null

            it.copy(
                phase = SessionPhase.LOBBY,
                selectedGameId = null,
                gameState = null,
                results = emptyMap()
            )
        }
    }

    fun playAgain() {
        _state.update { currentState ->
            currentState.selectedGameId?.let { gameId ->
                currentState.selectedConfig?.let { config ->

                    registry.get(gameId)?.let { newGame ->
                        game = newGame as Game<GameState, GameAction, GameConfig>

                        currentState.copy(
                            phase = SessionPhase.PLAYING,
                            gameState = newGame.initialState(currentState.players, config, currentState.gameState),
                            results = emptyMap()
                        )
                    }
                }
            } ?: currentState
        }
    }
}