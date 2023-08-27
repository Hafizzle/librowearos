package com.hafizzle.librowearos.presentation.audioplayer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hafizzle.librowearos.R
import com.hafizzle.librowearos.presentation.audioplayer.repository.AudiobookRepository
import kotlinx.coroutines.launch

class AudiobookViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AudiobookRepository = AudiobookRepository()
    private val _audiobooks = MutableLiveData<List<String>>(emptyList())
    val audiobooks: LiveData<List<String>> get() = _audiobooks

    fun fetchAudiobooks(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.fetchAudiobooks(query)
                _audiobooks.postValue(response.books.map { it.title }.filterNotNull())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Placeholder for now until we tie the API in
    fun getLocalAudiobooks() {
        val resources = getApplication<Application>().resources
        val rawResources = listOf(
            R.raw.comfort_book,
            R.raw.comfort_book,
            R.raw.comfort_book
        )

        val titles = rawResources.map {
            resources.getResourceEntryName(it)
        }

        _audiobooks.postValue(titles)
    }

}



