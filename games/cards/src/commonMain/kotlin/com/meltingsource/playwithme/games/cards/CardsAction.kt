package com.meltingsource.playwithme.games.cards

import com.meltingsource.playwithme.api.game.GameAction
import kotlinx.serialization.Serializable

@Serializable
sealed class CardsAction : GameAction {

    @Serializable
    data class Play(val cardId: String) : CardsAction()

    @Serializable
    object Draw : CardsAction()

    @Serializable
    object CollectToDiscard : CardsAction()

    @Serializable
    object CollectToTrick : CardsAction()

    @Serializable
    object Undo : CardsAction()
}