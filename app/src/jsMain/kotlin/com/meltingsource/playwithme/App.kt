package com.meltingsource.playwithme

import com.meltingsource.playwithme.api.Player
import com.meltingsource.playwithme.core.GameEngine
import com.meltingsource.playwithme.games.cards.CardsGame

val game = CardsGame()
val players = listOf(
    Player("1", "Alice"),
    Player("2", "Bob")
)

val engine = GameEngine(game, players)

//engine.dispatch(CardsAction.AddPoint("1"), players[0])
//engine.dispatch(CardsAction.EndTurn, players[0])