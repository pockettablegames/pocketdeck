package com.meltingsource.playwithme.games.cards

import com.meltingsource.playwithme.api.Player
import com.meltingsource.playwithme.api.game.Game

class CardsGame : Game<CardsState, CardsAction> {

    override val id: String = "cards"
    override val displayName: String = "Cards"

    override val stateSerializer = CardsState.serializer()
    override val actionSerializer = CardsAction.serializer()

    override fun initialState(players: List<Player>): CardsState {
        require(players.isNotEmpty())

        return CardsState(
            currentTurnPlayerId = players.first().id,
            scores = players.associate { it.id to 0 }
        )
    }

    override fun reduce(
        state: CardsState,
        action: CardsAction,
        actor: Player
    ): CardsState {

        return when (action) {

            is CardsAction.EndTurn -> {
                state.copy(
                    currentTurnPlayerId = nextPlayer(
                        state.currentTurnPlayerId,
                        state.scores.keys.toList()
                    )
                )
            }

            is CardsAction.AddPoint -> {
                val current = state.scores[action.playerId] ?: return state

                state.copy(
                    scores = state.scores + (action.playerId to current + 1)
                )
            }
        }
    }

    private fun nextPlayer(
        currentId: String,
        players: List<String>
    ): String {
        val index = players.indexOf(currentId)
        val nextIndex = (index + 1) % players.size
        return players[nextIndex]
    }
}
