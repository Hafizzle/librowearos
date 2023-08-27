package com.hafizzle.librowearos.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.hafizzle.librowearos.R

@Composable
fun MainMenuScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            NavigationPill(text = "Search", icon = painterResource(id = R.drawable.baseline_search_24)) {
                // todo :)
            }
        }
        item {
            NavigationPill(text = "Library", icon = painterResource(id = R.drawable.library_ic)) {
                navController.navigate("audioList")
            }
        }
        item {
            NavigationPill(text = "Explore", icon = painterResource(id = R.drawable.binoculars_ic)) {
                // todo :)
            }
        }
        item {
            NavigationPill(text = "Menu", icon = painterResource(id = R.drawable.baseline_menu_24)) {
                // todo :)
            }
        }
    }
}

@Composable
fun NavigationPill(
    text: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
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
            Icon(
                painter = icon,
                contentDescription = null,  // since it's just an icon for navigation
                tint = Color.Gray,
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp)) // Gap between image and text

            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}


