import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.meltingsource.playwithme.api.Player
import com.meltingsource.playwithme.core.GameEngine
import com.meltingsource.playwithme.core.GameRegistry
import com.meltingsource.playwithme.games.cards.CardsAction
import com.meltingsource.playwithme.games.cards.CardsGame

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(content = {
        App()
    })
}

@Composable
fun App() {
    val players = remember {
        listOf(
            Player("1", "Alice"),
            Player("2", "Bob")
        )
    }

    val registry = remember {
        GameRegistry().apply {
            register(CardsGame())
        }
    }

    val game = registry.get("cards") as CardsGame

    var engine by remember {
        mutableStateOf(
            GameEngine(game, players)
        )
    }

    var state by remember {
        mutableStateOf(engine.getState())
    }

    Column {
        Text("Cards Game (Local Test)")

        Text("Turn: ${state.currentTurnPlayerId}")

        state.scores.forEach { (id, score) ->
            Text("$id : $score")
        }

        Button(onClick = {
            engine.dispatch(
                CardsAction.AddPoint(state.currentTurnPlayerId),
                players.first { it.id == state.currentTurnPlayerId }
            )
            state = engine.getState()
        }
        ) {
            Text("Add Point")
        }

        Button(onClick = {
            engine.dispatch(
                CardsAction.EndTurn,
                players.first { it.id == state.currentTurnPlayerId }
            )
            state = engine.getState()
        }
        ) {
            Text("End Turn")
        }
    }
}
