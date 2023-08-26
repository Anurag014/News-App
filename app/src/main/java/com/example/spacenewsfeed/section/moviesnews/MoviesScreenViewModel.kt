package com.example.spacenewsfeed.section.moviesnews

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

sealed interface MoviesUiState {
    data class Success(val moviesNewsList: NewsArticleList): MoviesUiState
    object Error : MoviesUiState
    object Loading : MoviesUiState
}

class MoviesScreenViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    var moviesUiState: MoviesUiState by mutableStateOf(MoviesUiState.Loading)
        private set

    init {
        getMoviesNewsList()
    }

    fun getMoviesNewsList() {
        viewModelScope.launch {
            moviesUiState = try {
                MoviesUiState.Success(newsRepository.getMoviesNews())
            } catch (e: IOException) {
                MoviesUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val newsRepository = application.container.newsRepository
                MoviesScreenViewModel(newsRepository = newsRepository)
            }
        }
    }
}