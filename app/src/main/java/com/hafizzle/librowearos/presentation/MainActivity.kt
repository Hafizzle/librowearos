package com.hafizzle.librowearos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController

import com.hafizzle.librowearos.presentation.audiolist.AudiobookListScreen
import com.hafizzle.librowearos.presentation.audioplayer.AudioPlayerScreen
import com.hafizzle.librowearos.presentation.theme.LibrowearosTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.hafizzle.librowearos.presentation.audioplayer.AudiobookPlayer
import com.hafizzle.librowearos.presentation.audioplayer.viewmodel.AudiobookViewModel


class MainActivity : ComponentActivity() {

    private val viewModel: AudiobookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibrowearosTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "mainMenu") {
                    composable("mainMenu") {
                        MainMenuScreen(navController)
                    }
                    composable("audioList") {
                        AudiobookListScreen(navController, viewModel)
                    }
                    composable("audioPlayer/{audiobookUrl}") { backStackEntry ->
                        val audiobookUrl = backStackEntry.arguments?.getString("audiobookUrl") ?: ""
                        val audiobookPlayer = AudiobookPlayer(this@MainActivity)
                        AudioPlayerScreen(navController, audiobookUrl, audiobookPlayer)
                    }
                }

            }
        }
    }
}
