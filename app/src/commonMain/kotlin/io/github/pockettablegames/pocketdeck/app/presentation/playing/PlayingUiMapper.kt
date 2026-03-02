package io.github.pockettablegames.pocketdeck.app.presentation.playing

import io.github.pockettablegames.pocketdeck.api.session.SessionPhase
import io.github.pockettablegames.pocketdeck.api.session.SessionState
import io.github.pockettablegames.pocketdeck.games.cards.CardsState

object PlayingUiMapper {

    fun map(session: SessionState): PlayingUiState? {

        if (session.phase != SessionPhase.PLAYING) return null

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
            players = playerSummaries,
            hand = activePlayer.hand,
            table = cardsState.players.associate { it.playerId to it.table },
            deckCount = cardsState.deck.size,
            discard = cardsState.discard,
            tricks = activePlayer.tricks
        )
    }
}