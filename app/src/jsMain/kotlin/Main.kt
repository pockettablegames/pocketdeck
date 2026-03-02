import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.pockettablegames.pocketdeck.app.presentation.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(
    ) {
        App()
    }
}
