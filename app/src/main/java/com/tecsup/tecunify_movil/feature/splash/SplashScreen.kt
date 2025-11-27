package com.tecsup.tecunify_movil.feature.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.tecunify_movil.R

@Composable
fun SplashScreen() {

    // Animación del logo (bounce)
    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = { OvershootInterpolator(4f).getInterpolation(it) }
            )
        )
    }

    // Animación de la barra de carga (va y viene)
    val infiniteTransition = rememberInfiniteTransition(label = "progress")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "progressValue"
    )

    // Fondo degradado Tecsup
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF003B64),
            Color(0xFF0077C8),
            Color(0xFF5EC6F2)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // LOGO
            Image(
                painter = painterResource(id = R.drawable.tecunify),
                contentDescription = "TecUnify",
                modifier = Modifier
                    .size(350.dp)
                    .scale(scale.value)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // TEXTO PEQUEÑO ENCIMA DE LA BARRA
            androidx.compose.material3.Text(
                text = "TecUnify, la app para los espacios de TECSUP",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // BARRA DE CARGA ESTILO "LOADING..."
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.25f))
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    color = Color.White,
                    trackColor = Color.Transparent,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}