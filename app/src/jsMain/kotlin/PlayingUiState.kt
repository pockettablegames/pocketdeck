import com.meltingsource.playwithme.games.cards.Card

data class PlayingUiState(
    val activePlayerId: String,

    val players: List<PlayerSummaryUi>,

    val hand: List<Card>,
    val table: List<Card>,
    val deckCount: Int,
    val discard: List<Card>,
    val tricks: List<Card>,
    val itemsById: Map<String, Card>
)

data class PlayerSummaryUi(
    val id: String,
    val name: String,
    val handCount: Int,
    val trickCount: Int
)