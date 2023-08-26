package com.example.spacenewsfeed.section.sportsnews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spacenewsfeed.NewsApplication
import com.example.spacenewsfeed.models.NewsArticleList
import com.example.spacenewsfeed.repository.NewsRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface SportsUiState {
    data class Success(val sportsNewsList: NewsArticleList): SportsUiState
    object Error : SportsUiState
    object Loading : SportsUiState
}

class SportsScreenViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    var sportsUiState: SportsUiState by mutableStateOf(SportsUiState.Loading)
        private set

    init {
        getSportsNewsList()
    }

    fun getSportsNewsList() {
        viewModelScope.launch {
            sportsUiState = try {
                SportsUiState.Success(newsRepository.getSportsNews())
            } catch (e: IOException) {
                SportsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val newsRepository = application.container.newsRepository
                SportsScreenViewModel(newsRepository = newsRepository)
            }
        }
    }
}