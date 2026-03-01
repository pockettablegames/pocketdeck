package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.meltingsource.playwithme.app.theme.Theme

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