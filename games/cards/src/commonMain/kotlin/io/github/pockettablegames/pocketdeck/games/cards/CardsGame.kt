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
        config: CardsConfig,
        lastState: CardsState?
    ): CardsState {
        val deck = config.deck.shuffled().toMutableList()

        val playerCards = if (config.autoDeal) {
            if (config.distributeAllDeckCards) {
                val hands = List(players.size) {
                    mutableListOf<Card>()
                }

                deck.forEachIndexed { index, card ->
                    hands[index % hands.size].add(card)
                }
                players.mapIndexed { index, player ->
                    PlayerCards(
                        playerId = player.id,
                        hand = hands[index].toList(),
                        table = emptyList(),
                        tricks = emptyList()
                    )
                }.also {
                    deck.clear()
                }
            } else {
                players.map { player ->
                    PlayerCards(
                        playerId = player.id,
                        hand = deck.take(config.cardsPerPlayer).also {
                            deck.removeAll(it)
                        },
                        table = emptyList(),
                        tricks = emptyList()
                    )
                }
            }
        } else {
            players.map { player ->
                PlayerCards(
                    playerId = player.id,
                    hand = emptyList(),
                    table = emptyList(),
                    tricks = emptyList()
                )
            }
        }

        return CardsState(
            players = playerCards,
            deck = deck,
            discard = emptyList(),
            dealer = if (lastState != null) {
                players[
                    (players.indexOfFirst { it.id == lastState.dealer } + 1) % players.size
                ].id
            } else {
                players.first().id
            },
            action = null
        )
    }

    override fun apply(
        state: CardsState,
        action: CardsAction,
        actor: Player,
        config: CardsConfig
    ): CardsState {
        return when (action) {
            is CardsAction.Play -> play(state, action.cardId, actor)
            is CardsAction.Draw -> draw(config, state, actor)
            is CardsAction.CollectToDiscard -> collectToDiscard(state, actor)
            is CardsAction.CollectToTrick -> collectToTrick(state, actor)
            is CardsAction.Undo -> state.history.lastOrNull() ?: state
            is CardsAction.Deal -> deal(state, actor, config)
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
            players = updatedPlayers,
            action = PlayerAction(actor.id, ActionType.Play, listOf(card))
        )

        return withHistory(state, newState)
    }

    private fun draw(
        config: CardsConfig,
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

        var deck = state.deck.drop(1)
        var discard = state.discard
        if (deck.isEmpty() && config.refillDeckWithDiscard) {
            val lastDiscard = discard.last()
            deck = discard.dropLast(1).shuffled()
            discard = listOf(lastDiscard)
        }

        val newState = state.copy(
            players = updatedPlayers,
            deck = deck,
            discard = discard,
            action = PlayerAction(actor.id, ActionType.Draw, listOf(card))
        )

        return withHistory(state, newState)
    }

    private fun collectToDiscard(
        state: CardsState,
        actor: Player
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
            discard = state.discard + table,
            action = PlayerAction(actor.id, ActionType.CollectToDiscard, table)
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
            players = updatedPlayers,
            action = PlayerAction(actor.id, ActionType.CollectToTrick, table)
        )

        return withHistory(state, newState)
    }

    private fun deal(
        state: CardsState,
        actor: Player,
        config: CardsConfig
    ): CardsState {
        val deck = state.deck.toMutableList()

        val playerCards = if (config.distributeAllDeckCards) {
            val hands = state.players.map {
                it.hand.toMutableList()
            }

            deck.forEachIndexed { index, card ->
                hands[index % hands.size].add(card)
            }
            state.players.mapIndexed { index, player ->
                player.copy(hand = hands[index].toList())
            }.also {
                deck.clear()
            }
        } else {
            state.players.map { player ->
                player.copy(
                    hand = deck.take(config.cardsPerPlayer).also {
                        deck.removeAll(it)
                    }
                )
            }
        }

        val newState = state.copy(
            players = playerCards,
            deck = deck,
            action = PlayerAction(actor.id, ActionType.Deal, emptyList())
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