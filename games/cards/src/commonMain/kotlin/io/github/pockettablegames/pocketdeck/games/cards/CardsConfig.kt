package io.github.pockettablegames.pocketdeck.games.cards

import io.github.pockettablegames.pocketdeck.api.game.GameConfig
import kotlinx.serialization.Serializable

@Serializable
data class CardsConfig(
    val name: String = "Cards",
    val deck: List<Card> = emptyList(),
    val autoDeal: Boolean = true,
    val distributeAllDeckCards: Boolean = true,
    val cardsPerPlayer: Int = 3,
    val showDeckZone: DeckType = DeckType.FACE_DOWN,
    val showDiscardZone: DiscardType = DiscardType.FACE_UP,
    val showPlayerTrickZone: Boolean = true,
    val refillDeckWithDiscard: Boolean = false,
    val groupByRank: Boolean = false,
    val playersOrderClockwise: Boolean = false

) : GameConfig

enum class DiscardType {
    HIDDEN,
    FACE_UP,
    FACE_DOWN
}

enum class DeckType {
    HIDDEN,
    FACE_DOWN,
    FACE_DOWN_LAST_UP
}