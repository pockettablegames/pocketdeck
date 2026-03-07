package io.github.pockettablegames.pocketdeck.app.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.github.pockettablegames.pocketdeck.api.session.SessionPhase
import io.github.pockettablegames.pocketdeck.app.presentation.lobby.LobbyScreen
import io.github.pockettablegames.pocketdeck.app.presentation.playing.PlayerSwitchScreen
import io.github.pockettablegames.pocketdeck.app.presentation.playing.PlayingScreen
import io.github.pockettablegames.pocketdeck.app.presentation.playing.PlayingUiMapper
import io.github.pockettablegames.pocketdeck.app.presentation.results.ResultScreen
import io.github.pockettablegames.pocketdeck.app.presentation.score.ScoreEntryScreen
import io.github.pockettablegames.pocketdeck.app.presentation.setup.CardsConfigScreen
import io.github.pockettablegames.pocketdeck.app.theme.AppTheme
import io.github.pockettablegames.pocketdeck.games.cards.CardsConfig
import io.github.pockettablegames.pocketdeck.games.cards.CardsState

@Composable
fun App() {

    val controller = remember { AppController() }

    val sessionState by controller.sessionState.collectAsState()

    val playingUiState = PlayingUiMapper.map(sessionState)

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            when (sessionState.phase) {

                SessionPhase.LOBBY ->
                    LobbyScreen(
                        sessionState = sessionState,
                        onAddPlayer = { controller.addPlayer(it) },
                        onRemovePlayer = { controller.removePlayer(it) },
                        onContinue = {
                            controller.enterSetup("cards")
                        }
                    )

                SessionPhase.SETUP ->
                    CardsConfigScreen(
                        defaultConfig = sessionState.selectedConfig as? CardsConfig ?: CardsConfig(),
                        playersCount = sessionState.players.size,
                        onStart = {
                            controller.selectConfig(it)
                            controller.startGame()
                        }
                    )

                SessionPhase.PLAYING -> {

                    if (playingUiState != null) {
                        PlayingScreen(
                            uiState = playingUiState,
                            config = sessionState.selectedConfig as CardsConfig,
                            onAction = { controller.apply(it) },
                            onSwitchPlayer = {
                                controller.enterSwitchPlayer()
                            },
                            onEnterScore = {
                                controller.enterScore()
                            }
                        )
                    }
                }

                SessionPhase.SWITCH_PLAYER -> {

                    if (playingUiState != null) {
                        PlayerSwitchScreen(
                            playingUiState.players,
                            playingUiState.activePlayerId,
                            onSelectPlayer = {
                                controller.switchToPlayer(it)
                                controller.returnToGame()
                            },
                            onCancel = {
                                controller.returnToGame()
                            }
                        )
                    }
                }

                SessionPhase.SCORE -> {
                    val tricks = remember {
                        (sessionState.gameState as? CardsState)?.let { state ->
                            state.players.associate { it.playerId to it.tricks }
                        } ?: emptyMap()
                    }
                    val activePlayerId = remember(sessionState) {
                        sessionState.activePlayerId
                    }
                    val activePlayer = remember(sessionState){
                        sessionState.players.firstOrNull { it.id == activePlayerId }
                            ?: sessionState.players.first()
                    }

                    ScoreEntryScreen(
                        sessionState.players,
                        activePlayer,
                        tricks,
                        currentScore = sessionState.results[activePlayerId],
                        onScoreChange = { player, result ->
                            controller.updateScore(player, result)
                        },
                        onSwitchPlayer = {
                            controller.switchPlayer()
                        },
                        onConfirmPlayerScore = {
                            controller.enterResults()
                        }
                    )
                }

                SessionPhase.RESULTS ->
                    ResultScreen(
                        sessionState.players,
                        sessionState.results,
                        onReturnToLobby = {
                            controller.endGame()
                        },
                        onPlayAgain = {
                            controller.playAgain()
                        }
                    )
            }
        }
    }
}
