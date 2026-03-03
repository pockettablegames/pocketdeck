package io.github.pockettablegames.pocketdeck.games.cards

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: String,
    val rank: String,
    val suit: Suit,
    val rankOrder: Int,
    val suitOrder: Int
)