package com.meltingsource.playwithme.games.cards

import com.meltingsource.playwithme.api.Player
import com.meltingsource.playwithme.api.game.Game
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
        val deck = buildDeck().toMutableList()

        val playerCards = players.map { player ->
            PlayerCards(
                playerId = player.id,
                hand = deck.take(config.cardsPerPlayer).also {
                    deck.removeAll(it)
                },
                tricks = emptyList()
            )
        }

        return CardsState(
            players = playerCards,
            table = emptyList(),
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
            hand = player.hand.filterNot { it.id == cardId }
        )

        val updatedPlayers = state.players.map {
            if (it.playerId == actor.id) updatedPlayer else it
        }

        val newState = state.copy(
            players = updatedPlayers,
            table = state.table + card
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
        val newState = state.copy(
            discard = state.discard + state.table,
            table = emptyList()
        )

        return withHistory(state, newState)
    }

    private fun collectToTrick(
        state: CardsState,
        actor: Player
    ): CardsState {

        val player = state.players.first { it.playerId == actor.id }

        val updatedPlayer = player.copy(
            tricks = player.tricks + state.table
        )

        val updatedPlayers = state.players.map {
            if (it.playerId == actor.id) updatedPlayer else it
        }

        val newState = state.copy(
            players = updatedPlayers,
            table = emptyList()
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

    private fun buildDeck(): List<Card> {
        return (1..20).map {
            Card(
                id = it.toString(),
                rank = it.toString(),
                suit = "♠"
            )
        }
    }
}