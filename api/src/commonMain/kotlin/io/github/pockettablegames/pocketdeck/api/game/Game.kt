package io.github.pockettablegames.pocketdeck.api.game

import io.github.pockettablegames.pocketdeck.api.Player
import kotlinx.serialization.KSerializer

interface Game<
        S : GameState,
        A : GameAction,
        C : GameConfig
        > {

    val id: String
    val displayName: String

    val stateSerializer: KSerializer<S>
    val actionSerializer: KSerializer<A>
    val configSerializer: KSerializer<C>

    fun initialState(
        players: List<Player>,
        config: C
    ): S

    fun apply(
        state: S,
        action: A,
        actor: Player
    ): S
}