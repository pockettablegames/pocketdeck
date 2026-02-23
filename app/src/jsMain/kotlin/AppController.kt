import com.meltingsource.playwithme.api.game.GameAction
import com.meltingsource.playwithme.api.game.GameConfig
import com.meltingsource.playwithme.api.session.SessionState
import com.meltingsource.playwithme.core.GameRegistry
import com.meltingsource.playwithme.core.SessionManager
import com.meltingsource.playwithme.games.cards.CardsConfig
import com.meltingsource.playwithme.games.cards.CardsGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow

class AppController {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val sessionManager: SessionManager

    init {

        val registry = GameRegistry(
            games = listOf(
                CardsGame()
            )
        )

        sessionManager = SessionManager(
            sessionId = "local-session",
            registry = registry
        )

        // Temporary bootstrap for MVP
        bootstrap()
    }

    val sessionState: StateFlow<SessionState> = sessionManager.state

    // ------------------------
    // Public API for UI
    // ------------------------

    fun apply(action: GameAction) {
        sessionManager.apply(action)
    }

    fun addPlayer(name: String) {
        sessionManager.addPlayer(name)
    }

    fun switchPlayer() {
        sessionManager.switchToNextPlayer()
    }

    fun enterSetup(gameId: String) {
        sessionManager.enterSetup(gameId)
    }

    fun selectConfig(config: GameConfig) {
        sessionManager.selectConfig(config)
    }

    fun startGame() {
        sessionManager.startGame()
    }

    fun endGame() {
        sessionManager.endGame()
    }

    // ------------------------
    // Temporary bootstrap
    // ------------------------

    private fun bootstrap() {

        sessionManager.addPlayer("Alice")
        sessionManager.addPlayer("Bob")
        sessionManager.addPlayer("Vera")

        sessionManager.enterSetup("cards")

        sessionManager.selectConfig(
            CardsConfig(
                name = "Default",
                cardsPerPlayer = 3
            )
        )

        sessionManager.startGame()
    }
}