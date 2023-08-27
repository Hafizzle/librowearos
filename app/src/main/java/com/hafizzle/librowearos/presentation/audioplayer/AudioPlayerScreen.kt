package com.hafizzle.librowearos.presentation.audioplayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import com.hafizzle.librowearos.R
import kotlinx.coroutines.delay
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.hafizzle.librowearos.presentation.audiolist.formatAudiobookTitle

@Composable
fun AudioPlayerScreen(navController: NavController, audiobookTitle: String, audiobookPlayer: AudiobookPlayer) {
    var isPlaying by remember { mutableStateOf(false) }
    val currentPosition = remember { mutableStateOf(0L) }
    val totalDuration = remember { mutableStateOf(1L) }  // avoid division by zero
    val isInitialized = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val resId = context.resources.getIdentifier(audiobookTitle, "raw", context.packageName)
    val uri = "android.resource://${context.packageName}/$resId"

    val fastForwardIcon: Painter = painterResource(id = R.drawable.forward_15_ic)
    val rewindIcon: Painter = painterResource(id = R.drawable.reverse_15_ic)
    val pauseIcon: Painter = painterResource(id = R.drawable.baseline_pause_24)
    val playIcon: Painter = painterResource(id = R.drawable.baseline_play_arrow_24)
    val ellipsisIcon: Painter = painterResource(id = R.drawable.more_ic)
    val menuIcon: Painter = painterResource(id = R.drawable.baseline_menu_24)
    val placeholderImage: Painter = painterResource(id = R.drawable.image_placeholder_cover)

    LaunchedEffect(Unit) {
        delay(1000)
        if (!isInitialized.value) {
            audiobookPlayer.play(uri)
            isPlaying = true
            isInitialized.value = true
        }
    }



    // Periodically update the current position
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            val newDuration = audiobookPlayer.getDuration()
            if (newDuration > 0L) {
                totalDuration.value = newDuration
            }
            currentPosition.value = audiobookPlayer.getCurrentPosition().coerceIn(0L, totalDuration.value)
            delay(100)  // update every second
        }
    }




    Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Box(modifier = Modifier.align(Alignment.TopCenter).padding(16.dp)) {
            Row(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    painter = placeholderImage,
                    contentDescription = "Audiobook Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = formatAudiobookTitle(audiobookTitle),
                    color = Color.White,
                    style = MaterialTheme.typography.body2,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier.fillMaxSize(),
            color = Color.LightGray,
            strokeWidth = 4.dp
        )

        val targetProgress = if (totalDuration.value > 0L) {
            currentPosition.value.toFloat() / totalDuration.value.toFloat()
        } else 0f

        val animatedProgress by animateFloatAsState(targetValue = targetProgress)

        CircularProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF009688),
            strokeWidth = 4.dp
        )

        // Central controls
        Box(modifier = Modifier.align(Alignment.Center)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                IconButton(
                    onClick = { audiobookPlayer.seekTo(currentPosition.value - 15_000) }
                ) {
                    Icon(painter = rewindIcon, contentDescription = "Rewind", tint = Color.White, modifier = Modifier.size(28.dp))
                }

                IconButton(
                    onClick = {
                        if (isPlaying) {
                            audiobookPlayer.pause()
                            isPlaying = false
                        } else {
                            audiobookPlayer.play(uri)
                            isPlaying = true
                        }
                    },
                    modifier = Modifier.size(50.dp).background(color = Color.DarkGray.copy(alpha = 0.45f), shape = CircleShape).padding(horizontal = 6.dp)
                ) {
                    Icon(painter = if (isPlaying) pauseIcon else playIcon, contentDescription = if (isPlaying) "Pause" else "Play", tint = Color.White, modifier = Modifier.size(36.dp))
                }

                IconButton(
                    onClick = { audiobookPlayer.seekTo(currentPosition.value + 15_000) }) {
                    Icon(painter = fastForwardIcon, contentDescription = "Fast forward", tint = Color.White, modifier = Modifier.size(28.dp))
                }
            }
        }

        Row(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /* todo :) */ },
            ) {
                Icon(painter = menuIcon, contentDescription = "Menu", tint = Color.White, modifier = Modifier.size(16.dp))
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { /* todo :) */ },
            ) {
                Icon(painter = ellipsisIcon, contentDescription = "More actions", tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            audiobookPlayer.stop()
        }
    }
}






