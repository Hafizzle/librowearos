package com.hafizzle.librowearos.presentation.audiolist

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/feed/audiobooks/title/{query}")
    suspend fun getAudiobooksByTitle(@Path("query") query: String, @Query("format") format: String = "json"): LibriVoxResponse
}
