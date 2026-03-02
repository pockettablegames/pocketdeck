package io.github.pockettablegames.pocketdeck.games.cards

import io.github.pockettablegames.pocketdeck.api.Player
import io.github.pockettablegames.pocketdeck.api.game.Game
import kotlinx.serialization.KSerializer

class CardsGame : Game<CardsState, CardsAction, CardsConfig> {
    override val id: String = "cards"
    override val displayName: String = "Cards"

    override val stateSerializer: KSerializer<CardsState> = CardsState.serializer()
    override val actionSerializer: KSerializer<CardsAction> = CardsAction.serializer()
    override val configSerializer: KSerializer<CardsConfig> = CardsConfig.serializer()

    override fun initialState(
        players: List<Player>,
        config: CardsConfig
    ): CardsState {
        val deck = config.deck.toMutableList()

        val playerCards = players.map { player ->
            PlayerCards(
                playerId = player.id,
                hand = deck.take(config.cardsPerPlayer).also {
                    deck.removeAll(it)
                },
                table = emptyList(),
                tricks = emptyList()
            )
        }

        return CardsState(
            players = playerCards,
            deck = deck,
            discard = emptyList()
        )
    }

    override fun apply(
        state: CardsState,
        action: CardsAction,
        actor: Player
    ): CardsState {
        return when (action) {
            is CardsAction.Play -> play(state, action.cardId, actor)
            is CardsAction.Draw -> draw(state, actor)
            is CardsAction.CollectToDiscard -> collectToDiscard(state)
            is CardsAction.CollectToTrick -> collectToTrick(state, actor)
            is CardsAction.Undo -> state.history.lastOrNull() ?: state
        }
    }

    private fun play(
        state: CardsState,
        cardId: String,
        actor: Player
    ): CardsState {
        val player = state.players.first { it.playerId == actor.id }
        val card = player.hand.first { it.id == cardId }

        val updatedPlayer = player.copy(
            hand = player.hand.filterNot { it.id == cardId },
            table = player.table + card
        )

        val updatedPlayers = state.players.map {
            if (it.playerId == actor.id) updatedPlayer else it
        }

        val newState = state.copy(
            players = updatedPlayers
        )

        return withHistory(state, newState)
    }

    private fun draw(
        state: CardsState,
        actor: Player
    ): CardsState {

        if (state.deck.isEmpty()) return state

        val card = state.deck.first()
        val player = state.players.first { it.playerId == actor.id }

        val updatedPlayer = player.copy(
            hand = player.hand + card
        )

        val updatedPlayers = state.players.map {
            if (it.playerId == actor.id) updatedPlayer else it
        }

        val newState = state.copy(
            players = updatedPlayers,
            deck = state.deck.drop(1)
        )

        return withHistory(state, newState)
    }

    private fun collectToDiscard(
        state: CardsState
    ): CardsState {
        val table = state.players.flatMap { it.table }
        if (table.isEmpty()) {
            return state
        }

        val updatedPlayers = state.players.map {
            it.copy(table = emptyList())
        }
        val newState = state.copy(
            players = updatedPlayers,
            discard = state.discard + table
        )

        return withHistory(state, newState)
    }

    private fun collectToTrick(
        state: CardsState,
        actor: Player
    ): CardsState {
        val table = state.players.flatMap { it.table }
        if (table.isEmpty()) {
            return state
        }

        val updatedPlayers = state.players.map { playerCards ->
            if (playerCards.playerId == actor.id) {
                playerCards.copy(
                    tricks = playerCards.tricks.toMutableList().also {
                        it.add(table)
                    },
                    table = emptyList()
                )
            } else {
                playerCards.copy(table = emptyList())
            }
        }

        val newState = state.copy(
            players = updatedPlayers
        )

        return withHistory(state, newState)
    }

    private fun withHistory(
        previous: CardsState,
        current: CardsState
    ): CardsState {
        return current.copy(
            history = previous.history + previous
        )
    }
}