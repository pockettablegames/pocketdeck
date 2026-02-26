import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.meltingsource.playwithme.app.presentation.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(
    ) {
        App()
    }
}
