package io.github.pockettablegames.pocketdeck.app.presentation.playing

import io.github.pockettablegames.pocketdeck.app.presentation.playerswitch.ActionUi
import io.github.pockettablegames.pocketdeck.games.cards.Card

data class PlayingUiState(
    val activePlayerId: String,
    val isDealer: Boolean,
    val undoEnabled: Boolean,

    val players: List<PlayerSummaryUi>,

    val hand: List<Card>,
    val table: Map<String, List<Card>>,
    val deck: List<Card>,
    val discard: List<Card>,
    val tricks: List<List<Card>>,
    val actions: List<ActionUi>
)

data class PlayerSummaryUi(
    val id: String,
    val name: String,
    val avatar: Int,
    val handCount: Int,
    val trickCount: Int
)