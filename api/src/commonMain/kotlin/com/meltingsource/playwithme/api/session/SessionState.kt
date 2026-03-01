package com.meltingsource.playwithme.api.session

import com.meltingsource.playwithme.api.Player
import com.meltingsource.playwithme.api.game.GameConfig
import com.meltingsource.playwithme.api.game.GameState
import kotlinx.serialization.Serializable

@Serializable
data class SessionState(
    val sessionId: String,

    val players: List<Player> = emptyList(),
    val activePlayerId: String? = null,

    val phase: SessionPhase = SessionPhase.LOBBY,

    val selectedGameId: String? = null,
    val selectedConfig: GameConfig? = null,

    val gameState: GameState? = null,

    val results: Map<String, Int> = emptyMap()
)