package io.github.pockettablegames.pocketdeck.app.presentation.playing

import io.github.pockettablegames.pocketdeck.api.session.SessionPhase
import io.github.pockettablegames.pocketdeck.api.session.SessionState
import io.github.pockettablegames.pocketdeck.app.presentation.playerswitch.ActionUi
import io.github.pockettablegames.pocketdeck.games.cards.ActionType
import io.github.pockettablegames.pocketdeck.games.cards.Card
import io.github.pockettablegames.pocketdeck.games.cards.CardsConfig
import io.github.pockettablegames.pocketdeck.games.cards.CardsState

object PlayingUiMapper {

    fun map(session: SessionState): PlayingUiState? {

        if (session.phase != SessionPhase.PLAYING
            && session.phase != SessionPhase.SWITCH_PLAYER
        ) return null

        val cardsState = session.gameState as? CardsState ?: return null
        val config = session.selectedConfig as? CardsConfig ?: return null

        val activeId = session.activePlayerId ?: return null

        val activePlayer = cardsState.players.first { it.playerId == activeId }

        val playerSummaries = session.players.map { player ->
            val playerCards = cardsState.players.first { it.playerId == player.id }

            PlayerSummaryUi(
                id = player.id,
                name = player.name,
                avatar = player.avatar,
                handCount = playerCards.hand.size,
                trickCount = playerCards.tricks.size
            )
        }

        return PlayingUiState(
            activePlayerId = activeId,
            isDealer = activeId == cardsState.dealer,
            undoEnabled = cardsState.action?.playerId == activeId,
            players = playerSummaries,
            hand = if (config.groupByRank) {
                activePlayer.hand.sortedWith(
                    compareByDescending<Card> {
                        it.rankOrder
                    }.thenBy { it.suitOrder }
                )
            } else {
                activePlayer.hand.sortedWith(
                    compareBy<Card> {
                        it.suitOrder
                    }.thenByDescending { it.rankOrder }
                )
            },
            table = cardsState.players.associate { it.playerId to it.table },
            deck = cardsState.deck,
            discard = cardsState.discard,
            tricks = activePlayer.tricks,
            actions = (cardsState.history + cardsState).mapNotNull { state ->
                state.action?.let { playerAction ->
                    when (val action = playerAction.actionType) {
                        ActionType.Draw -> {
                            ActionUi(
                                playerId = playerAction.playerId,
                                type = action,
                                label = "draw",
                                count = playerAction.cards.size,
                                cards = playerAction.cards
                            )
                        }

                        ActionType.Play -> {
                            ActionUi(
                                playerId = playerAction.playerId,
                                type = action,
                                label = "play",
                                count = playerAction.cards.size,
                                cards = playerAction.cards
                            )
                        }

                        ActionType.Deal -> {
                            ActionUi(
                                playerId = playerAction.playerId,
                                type = action,
                                label = "deal",
                                count = 1,
                                cards = playerAction.cards
                            )
                        }

                        ActionType.CollectToTrick -> {
                            ActionUi(
                                playerId = playerAction.playerId,
                                type = action,
                                label = "trick",
                                count = playerAction.cards.size,
                                cards = playerAction.cards
                            )
                        }

                        ActionType.CollectToDiscard -> {
                            ActionUi(
                                playerId = playerAction.playerId,
                                type = action,
                                label = "discard",
                                count = playerAction.cards.size,
                                cards = playerAction.cards
                            )
                        }
                    }
                }
            }
        )
    }
}