package com.meltingsource.playwithme.api.game

import kotlinx.serialization.Serializable

@Serializable
data class GameMessage(
    val gameId: String,
    val payload: String
)