package io.github.pockettablegames.pocketdeck.games.cards

import io.github.pockettablegames.pocketdeck.api.game.GameConfig
import kotlinx.serialization.Serializable

@Serializable
data class CardsConfig(
    val name: String = "Cards",
    val deck: List<Card> = emptyList(),
    val cardsPerPlayer: Int = 3,
    val showDeckZone: Boolean = true,
    val showDiscardZone: Boolean = true,
    val showPlayerTrickZone: Boolean = true,
    val refillDeckWithDiscard: Boolean = false,
    val groupByRank: Boolean = false,
    val discardHidesCardsFace: Boolean = false,
    val revealLastDeckCard: Boolean = false,
    val autoDeal: Boolean = true,
    val playersOrderClockwise: Boolean = false

) : GameConfig