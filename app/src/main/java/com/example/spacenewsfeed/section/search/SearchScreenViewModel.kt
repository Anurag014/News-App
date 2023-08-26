package com.example.spacenewsfeed.section.search

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

sealed interface SearchUiState {
    data class Success(val newsList: NewsArticleList): SearchUiState
    object Error : SearchUiState
    object Loading : SearchUiState
}
class SearchScreenViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    var searchUiState: SearchUiState by mutableStateOf(SearchUiState.Loading)
        private set

    fun searchNewsList(text: String) {
        viewModelScope.launch {
            searchUiState = try {
                SearchUiState.Success(newsRepository.searchNews(text))
            } catch (e: IOException) {
                SearchUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val newsRepository = application.container.newsRepository
                SearchScreenViewModel(newsRepository = newsRepository)
            }
        }
    }
}