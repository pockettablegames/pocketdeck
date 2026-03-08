package io.github.pockettablegames.pocketdeck.games.cards

import io.github.pockettablegames.pocketdeck.api.game.GameState
import kotlinx.serialization.Serializable

@Serializable
data class CardsState(
    val players: List<PlayerCards>,
    val deck: List<Card>,
    val discard: List<Card>,
    val dealer: String,
    val action: PlayerAction?,
    val history: List<CardsState> = emptyList()
) : GameState

@Serializable
data class PlayerCards(
    val playerId: String,
    val hand: List<Card>,
    val tricks: List<List<Card>>,
    val table: List<Card>
)

@Serializable
data class PlayerAction(
    val playerId: String,
    val actionType: ActionType,
    val cards: List<Card>
)

enum class ActionType {
    Play,
    Draw,
    Deal,
    CollectToDiscard,
    CollectToTrick
}