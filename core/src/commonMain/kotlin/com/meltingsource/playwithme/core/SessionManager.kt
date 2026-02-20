package com.meltingsource.playwithme.core

import com.meltingsource.playwithme.api.Player
import com.meltingsource.playwithme.api.session.SessionState

class SessionManager(
    private val sessionId: String
) {

    private var state = SessionState(sessionId = sessionId)

    fun getState(): SessionState = state

    fun addPlayer(player: Player) {
        if (state.started) return

        state = state.copy(
            players = state.players + player
        )
    }

    fun startGame(gameId: String) {
        if (state.started) return

        state = state.copy(
            started = true,
            activeGameId = gameId
        )
    }
}
