package io.github.pockettablegames.pocketdeck.app.presentation.playerswitch

import io.github.pockettablegames.pocketdeck.games.cards.ActionType
import io.github.pockettablegames.pocketdeck.games.cards.Card

data class ActionUi(
    val playerId: String,
    val type: ActionType,
    val label: String,
    val count: Int,
    val cards: List<Card>
)