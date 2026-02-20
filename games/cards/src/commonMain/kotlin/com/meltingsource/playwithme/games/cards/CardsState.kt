package com.meltingsource.playwithme.games.cards

import com.meltingsource.playwithme.api.game.GameState
import kotlinx.serialization.Serializable

@Serializable
data class CardsState(
    val currentTurnPlayerId: String,
    val scores: Map<String, Int>
) : GameState