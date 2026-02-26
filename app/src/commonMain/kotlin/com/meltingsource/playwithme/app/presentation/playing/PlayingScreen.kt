package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.api.game.GameAction
import com.meltingsource.playwithme.games.cards.CardsAction

@Composable
fun PlayingScreen(
    uiState: PlayingUiState,
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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    val activePlayer = remember(uiState) {
                        uiState.players.firstOrNull { it.id == uiState.activePlayerId }
                    }
                    val otherPlayers = remember(uiState) {
                        uiState.players.filterNot { it.id == uiState.activePlayerId }
                    }

                    PlayersOverviewRow(
                        players = otherPlayers,
                        maxWidth = maxWith
                    )

                    TableSection(
                        players = otherPlayers,
                        activePlayer = activePlayer,
                        table = uiState.table,
                        modifier = Modifier.fillMaxWidth(),
                        maxWidth = maxWith
                    )

                    DeckDiscardRow(
                        deckCount = uiState.deckCount,
                        discard = uiState.discard,
                        onDraw = { onAction(CardsAction.Draw) },
                        onCollectDiscard = {
                            onAction(CardsAction.CollectToDiscard)
                        }
                    )

                    activePlayer?.let {
                        PersonalSection(
                            playerName = it.name,
                            hand = uiState.hand,
                            tricks = uiState.tricks,
                            onPlay = { cardId ->
                                onAction(CardsAction.Play(cardId))
                            },
                            onCollectTrick = {
                                onAction(CardsAction.CollectToTrick)
                            },
                            modifier = Modifier
                                .weight(0.4f)
                                .fillMaxWidth(),
                            maxWidth = maxWith
                        )
                    }
                }
            }
        }
    }
}