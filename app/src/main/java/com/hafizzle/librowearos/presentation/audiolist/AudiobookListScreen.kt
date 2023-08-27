package com.hafizzle.librowearos.presentation.audiolist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.hafizzle.librowearos.presentation.audioplayer.viewmodel.AudiobookViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import com.hafizzle.librowearos.R


@Composable
fun AudiobookListScreen(navController: NavController, viewModel: AudiobookViewModel) {
    val audiobooks: List<String> by viewModel.audiobooks.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.library_ic),
                contentDescription = "Library Icon",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Library",
                color = Color.White,
                style = MaterialTheme.typography.title1,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(audiobooks) { audiobookTitle ->
                AudiobookPill(
                    audiobookTitle = audiobookTitle,
                    subtext = "Author", // todo - placeholder for now
                    navController = navController,
                    coverImage = painterResource(id = R.drawable.image_placeholder_cover)
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        // Local (Placeholder for now)
        viewModel.getLocalAudiobooks()

        // Remote
//        viewModel.fetchAudiobooks("mystery")
    }
}




@Composable
fun AudiobookPill(audiobookTitle: String, subtext: String, navController: NavController, coverImage: Painter) {
    Box(
        modifier = Modifier
            .clickable(onClick = {
                val route = "audioPlayer/$audiobookTitle"
                navController.navigate(route)
            })
            .background(Color.DarkGray.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))
            .padding(8.dp)
            .height(54.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = coverImage,
                contentDescription = "Audiobook Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = formatAudiobookTitle(audiobookTitle),
                    color = Color.White,
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp)
                )

                Text(
                    text = subtext,
                    color = Color.Gray,  // Change to desired color
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}


// todo - move into extension
fun formatAudiobookTitle(title: String): String {
    return title.replace('_', ' ').split(' ').joinToString(" ") { it.capitalize() }
}













