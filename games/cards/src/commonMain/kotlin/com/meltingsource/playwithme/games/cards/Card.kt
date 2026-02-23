package com.meltingsource.playwithme.games.cards

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: String,
    val rank: String,
    val suit: String
)