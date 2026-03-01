package com.meltingsource.playwithme.core

import com.meltingsource.playwithme.api.Player
import com.meltingsource.playwithme.api.game.Game
import com.meltingsource.playwithme.api.game.GameAction
import com.meltingsource.playwithme.api.game.GameConfig
import com.meltingsource.playwithme.api.game.GameState
import com.meltingsource.playwithme.api.session.SessionPhase
import com.meltingsource.playwithme.api.session.SessionState
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

    // ------------------------
    // Setup
    // ------------------------

    fun enterSetup(gameId: String) {
        _state.update {
            it.copy(
                phase = SessionPhase.SETUP,
                selectedGameId = gameId,
                selectedConfig = null
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
                            gameState = newGame.initialState(currentState.players, config)
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

                    currentState.gameState?.let { gameState ->
                        game?.apply(gameState, action, actor)?.let { newGameState ->
                            currentState.copy(
                                gameState = newGameState
                            )
                        }
                    }
                }
            } ?: currentState
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

    fun endGame() {
        _state.update {
            game = null

            it.copy(
                phase = SessionPhase.LOBBY,
                selectedGameId = null,
                selectedConfig = null,
                gameState = null
            )
        }
    }
}