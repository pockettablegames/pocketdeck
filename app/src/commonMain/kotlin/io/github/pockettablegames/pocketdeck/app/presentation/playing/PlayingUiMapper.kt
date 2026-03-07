package io.github.pockettablegames.pocketdeck.app.presentation.playing

import io.github.pockettablegames.pocketdeck.api.session.SessionPhase
import io.github.pockettablegames.pocketdeck.api.session.SessionState
import io.github.pockettablegames.pocketdeck.games.cards.Card
import io.github.pockettablegames.pocketdeck.games.cards.CardsConfig
import io.github.pockettablegames.pocketdeck.games.cards.CardsState

object PlayingUiMapper {

    fun map(session: SessionState): PlayingUiState? {

        if (session.phase != SessionPhase.PLAYING
            && session.phase != SessionPhase.SWITCH_PLAYER
        ) return null

        val cardsState = session.gameState as? CardsState ?: return null

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
            players = playerSummaries,
            hand = if((session.selectedConfig as? CardsConfig)?.groupByRank == true) {
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
            tricks = activePlayer.tricks
        )
    }
}