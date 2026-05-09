package com.example.spaceexpo.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.glassmorphism(
    blurRadius: Float = 20f,
    opacity: Float = 0.2f,
) = this.then(
    Modifier
        .graphicsLayer {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                renderEffect = android.graphics.RenderEffect.createBlurEffect(
                    blurRadius, blurRadius, android.graphics.Shader.TileMode.MIRROR
                ).asComposeRenderEffect()
            }
            compositingStrategy = CompositingStrategy.Offscreen
        }
        .drawWithContent {
            drawContent()
            drawRect(
                color = Color.White.copy(alpha = opacity),
                blendMode = BlendMode.Overlay
            )
        }
)
