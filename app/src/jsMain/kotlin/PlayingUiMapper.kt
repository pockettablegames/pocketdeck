import com.meltingsource.playwithme.api.session.SessionPhase
import com.meltingsource.playwithme.api.session.SessionState
import com.meltingsource.playwithme.games.cards.Card
import com.meltingsource.playwithme.games.cards.CardsState
import com.meltingsource.playwithme.games.cards.PlayerCards

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
                handCount = playerCards.hand.size,
                trickCount = playerCards.tricks.size
            )
        }

        val items = buildItemsMap(cardsState)

        return PlayingUiState(
            activePlayerId = activeId,
            players = playerSummaries,
            hand = activePlayer.hand,
            table = cardsState.table,
            deckCount = cardsState.deck.size,
            discard = cardsState.discard,
            tricks = activePlayer.tricks,
            itemsById = items
        )
    }

    private fun buildItemsMap(
        state: CardsState
    ): Map<String, Card> {

        val allCards =
            state.players.flatMap { it.hand + it.tricks } +
                    state.table +
                    state.deck +
                    state.discard

        return allCards.associateBy { it.id }
    }
}