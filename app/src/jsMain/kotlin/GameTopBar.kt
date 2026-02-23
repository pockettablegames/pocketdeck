import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    onUndo: () -> Unit,
    onSwitch: () -> Unit
) {
    TopAppBar(
        title = { Text("Cards") },
        actions = {
            TextButton(onClick = onUndo) {
                Text("Undo")
            }

            TextButton(onClick = onSwitch) {
                Text("Switch")
            }
        }
    )
}