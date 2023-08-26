package com.example.spacenewsfeed.section.sciencenews

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

sealed interface ScienceNewsUiState {
    data class Success(val scienceNewsList: NewsArticleList): ScienceNewsUiState
    object Error : ScienceNewsUiState
    object Loading : ScienceNewsUiState
}

class ScienceNewsScreenViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    var scienceNewsUiState: ScienceNewsUiState by mutableStateOf(ScienceNewsUiState.Loading)
        private set

    init {
        getScienceNewsList()
    }

    fun getScienceNewsList() {
        viewModelScope.launch {
            scienceNewsUiState = try {
                ScienceNewsUiState.Success(newsRepository.getScienceNews())
            } catch (e: IOException) {
                ScienceNewsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val newsRepository = application.container.newsRepository
                ScienceNewsScreenViewModel(newsRepository = newsRepository)
            }
        }
    }
}