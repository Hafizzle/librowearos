package com.hafizzle.librowearos.presentation.audioplayer.repository

import com.hafizzle.librowearos.presentation.audiolist.ApiClient
import com.hafizzle.librowearos.presentation.audiolist.LibriVoxResponse

class AudiobookRepository {

    private val apiService = ApiClient.apiService

    suspend fun fetchAudiobooks(query: String): LibriVoxResponse {
        return apiService.getAudiobooksByTitle(query)
    }

}
