package io.github.pockettablegames.pocketdeck.games.cards

import io.github.pockettablegames.pocketdeck.api.game.GameAction
import kotlinx.serialization.Serializable

@Serializable
sealed class CardsAction : GameAction {
    abstract val type: String

    @Serializable
    data class Play(val cardId: String) : CardsAction() {
        override val type: String = TYPE

        companion object {
            const val TYPE = "Play"
        }
    }

    @Serializable
    object Draw : CardsAction() {
        override val type: String = "Draw"
    }

    @Serializable
    object CollectToDiscard : CardsAction() {
        override val type: String = "CollectToDiscard"
    }

    @Serializable
    object CollectToTrick : CardsAction() {
        override val type: String = "CollectToTrick"
    }

    @Serializable
    object Undo : CardsAction() {
        override val type: String = "Undo"
    }

    @Serializable
    object Deal : CardsAction() {
        override val type: String = "Deal"
    }

}