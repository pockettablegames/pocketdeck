package com.meltingsource.playwithme.app.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.meltingsource.playwithme.api.session.SessionPhase
import com.meltingsource.playwithme.app.presentation.lobby.LobbyScreen
import com.meltingsource.playwithme.app.presentation.playing.PlayingScreen
import com.meltingsource.playwithme.app.presentation.playing.PlayingUiMapper
import com.meltingsource.playwithme.app.theme.AppTheme
import com.meltingsource.playwithme.games.cards.CardsConfig

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
                        onStart = {
                            controller.startGame()
                        }
                    )

                SessionPhase.SETUP ->
                    Text("Setup")

                SessionPhase.PLAYING -> {

                    if (playingUiState != null) {
                        PlayingScreen(
                            uiState = playingUiState,
                            config = sessionState.selectedConfig as CardsConfig,
                            onAction = { controller.apply(it) },
                            onSwitchPlayer = {
                                controller.switchPlayer()
                            }
                        )
                    }
                }

                SessionPhase.RESULTS ->
                    Text("Results")
            }
        }
    }
}
