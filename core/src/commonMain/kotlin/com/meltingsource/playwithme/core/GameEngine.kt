package com.meltingsource.playwithme.core

import com.meltingsource.playwithme.api.Player
import com.meltingsource.playwithme.api.game.Game
import com.meltingsource.playwithme.api.game.GameAction
import com.meltingsource.playwithme.api.game.GameState

class GameEngine<S : GameState, A : GameAction>(
    private val game: Game<S, A>,
    players: List<Player>
) {

    private var state: S = game.initialState(players)

    fun getState(): S = state

    fun dispatch(action: A, actor: Player) {
        state = game.reduce(state, action, actor)
    }
}