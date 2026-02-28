package com.meltingsource.playwithme.games.cards

import com.meltingsource.playwithme.api.game.GameConfig
import kotlinx.serialization.Serializable

@Serializable
data class CardsConfig(
    val name: String,
    val deck: List<Card>,
    val cardsPerPlayer: Int,
    val showDeckZone: Boolean,
    val showDiscardZone: Boolean,
    val showPlayerTrickZone: Boolean,
    val stackCardsOnTable: Boolean,
    val refillDeckWithDiscard: Boolean,
    val groupByRank: Boolean,
    val remainingCardsAfterDealGoesToDeck: Boolean,
    val discardHidesCardsFace: Boolean,
    val revealLastDeckCard: Boolean,
    val autoDeal: Boolean

) : GameConfig