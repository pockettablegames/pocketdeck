package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.api.game.GameAction
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.games.cards.CardsAction
import com.meltingsource.playwithme.games.cards.CardsConfig

@Composable
fun PlayingScreen(
    uiState: PlayingUiState,
    config: CardsConfig,
    onAction: (GameAction) -> Unit,
    onSwitchPlayer: () -> Unit
) {
    Scaffold(
        topBar = {
            GameTopBar(
                onUndo = { onAction(CardsAction.Undo) },
                onSwitch = onSwitchPlayer
            )
        }
    ) { padding ->

        Box(
            Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BoxWithConstraints(
                modifier = Modifier
                .widthIn(max = 800.dp)
                .heightIn(max = 600.dp)
                .fillMaxSize()
            ) {
                val maxWith = this.maxWidth
                val activePlayer = remember(uiState) {
                    uiState.players.firstOrNull { it.id == uiState.activePlayerId }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Theme.Spacing.medium)
                ) {

                    item {
                        PlayersOverviewRow(
                            players = uiState.players,
                            activePlayer = activePlayer,
                            maxWidth = maxWith,
                            config = config
                        )
                    }

                    item {
                        TableSection(
                            players = uiState.players,
                            activePlayer = activePlayer,
                            table = uiState.table,
                            discard = uiState.discard,
                            modifier = Modifier.fillMaxWidth(),
                            maxWidth = maxWith,
                            deckCount = uiState.deckCount,
                            config = config,
                            onDraw = { onAction(CardsAction.Draw) },
                            onCollectDiscard = {
                                onAction(CardsAction.CollectToDiscard)
                            }
                        )
                    }

                    activePlayer?.let {
                        item {
                            PersonalSection(
                                player = it,
                                hand = uiState.hand,
                                tricks = uiState.tricks,
                                onPlay = { cardId ->
                                    onAction(CardsAction.Play(cardId))
                                },
                                onCollectTrick = {
                                    onAction(CardsAction.CollectToTrick)
                                },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            }
        }
    }
}