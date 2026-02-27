package com.meltingsource.playwithme.app.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meltingsource.playwithme.app.theme.Theme.AppShapes
import com.meltingsource.playwithme.app.theme.Theme.AppTypography
import com.meltingsource.playwithme.app.theme.Theme.LightColorScheme
import org.jetbrains.compose.resources.DrawableResource
import playwithme.app.generated.resources.Res
import playwithme.app.generated.resources.avatar_0
import playwithme.app.generated.resources.avatar_1
import playwithme.app.generated.resources.avatar_2
import playwithme.app.generated.resources.avatar_3
import playwithme.app.generated.resources.avatar_4
import playwithme.app.generated.resources.avatar_5

object Theme {
    object Spacing {
        val small: Dp = 4.dp
        val medium: Dp = 8.dp
        val large: Dp = 16.dp
        val xlarge: Dp = 24.dp
    }

    object Card {
        val width: Dp = 48.dp
        val height: Dp = 64.dp
        val elevation: Dp = 1.dp
        val shape: Shape = RoundedCornerShape(8.0.dp)
        val color: Color = Color.White
    }

    object Table {
        val shape: Shape = RoundedCornerShape(16.0.dp)
    }

    val LightColorScheme = lightColorScheme(

        primary = Color(0xFF3F51B5),
        onPrimary = Color.White,

        primaryContainer = Color(0xFFE8EAF6),
        onPrimaryContainer = Color(0xFF1A237E),

        secondary = Color(0xFF546E7A),
        onSecondary = Color.White,

        background = Color(0xFFF6F7F9),
        onBackground = Color(0xFF1C1C1E),

        surface = Color(0xFFFFFFFF),
        onSurface = Color(0xFF1C1C1E),

        surfaceVariant = Color(0xFFF1F3F6),
        onSurfaceVariant = Color(0xFF4F5B62),

        outline = Color(0xFFE0E3E7)
    )

    val AppShapes = Shapes(
        extraSmall = RoundedCornerShape(6.dp),
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(12.dp),
        large = RoundedCornerShape(20.dp),
        extraLarge = RoundedCornerShape(28.dp)
    )

    val AppTypography = Typography(
        titleLarge = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        ),
        titleMedium = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        bodyMedium = TextStyle(
            fontSize = 14.sp
        ),
        labelSmall = TextStyle(
            fontSize = 11.sp
        )
    )
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

@Composable
fun rememberAvatars() : List<DrawableResource> {
    return remember {
        listOf(
            Res.drawable.avatar_0,
            Res.drawable.avatar_1,
            Res.drawable.avatar_2,
            Res.drawable.avatar_3,
            Res.drawable.avatar_4,
            Res.drawable.avatar_5
        )
    }
}
