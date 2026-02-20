package com.meltingsource.playwithme.api.game

import com.meltingsource.playwithme.api.Player
import kotlinx.serialization.KSerializer

interface Game<S : GameState, A : GameAction> {

    val id: String
    val displayName: String

    val stateSerializer: KSerializer<S>
    val actionSerializer: KSerializer<A>

    fun initialState(players: List<Player>): S

    fun reduce(
        state: S,
        action: A,
        actor: Player
    ): S
}