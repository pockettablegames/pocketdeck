package com.meltingsource.playwithme.games.cards

import com.meltingsource.playwithme.api.game.GameAction
import kotlinx.serialization.Serializable

@Serializable
sealed interface CardsAction : GameAction {

    @Serializable
    data object EndTurn : CardsAction

    @Serializable
    data class AddPoint(val playerId: String) : CardsAction
}