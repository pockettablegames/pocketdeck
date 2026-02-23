package com.meltingsource.playwithme.games.cards

import com.meltingsource.playwithme.api.game.GameConfig
import kotlinx.serialization.Serializable

@Serializable
data class CardsConfig(
    val name: String,
    val cardsPerPlayer: Int
) : GameConfig