package com.meltingsource.playwithme.app.presentation.playing

import com.meltingsource.playwithme.games.cards.Card

data class PlayingUiState(
    val activePlayerId: String,

    val players: List<PlayerSummaryUi>,

    val hand: List<Card>,
    val table: Map<String, List<Card>>,
    val deckCount: Int,
    val discard: List<Card>,
    val tricks: List<List<Card>>
)

data class PlayerSummaryUi(
    val id: String,
    val name: String,
    val avatar: Int,
    val handCount: Int,
    val trickCount: Int
)