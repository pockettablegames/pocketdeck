package io.github.pockettablegames.pocketdeck.api.session

import io.github.pockettablegames.pocketdeck.api.Player
import io.github.pockettablegames.pocketdeck.api.game.GameConfig
import io.github.pockettablegames.pocketdeck.api.game.GameState
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