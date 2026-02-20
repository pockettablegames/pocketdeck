package com.meltingsource.playwithme.api.session

import com.meltingsource.playwithme.api.Player
import kotlinx.serialization.Serializable

@Serializable
data class SessionState(
    val sessionId: String,
    val players: List<Player> = emptyList(),
    val started: Boolean = false,
    val activeGameId: String? = null
)