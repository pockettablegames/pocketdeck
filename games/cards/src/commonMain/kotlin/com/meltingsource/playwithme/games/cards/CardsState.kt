package com.meltingsource.playwithme.games.cards

import com.meltingsource.playwithme.api.game.GameState
import kotlinx.serialization.Serializable

@Serializable
data class CardsState(
    val players: List<PlayerCards>,
    val table: List<Card>,
    val deck: List<Card>,
    val discard: List<Card>,
    val history: List<CardsState> = emptyList()
) : GameState

@Serializable
data class PlayerCards(
    val playerId: String,
    val hand: List<Card>,
    val tricks: List<Card>
)