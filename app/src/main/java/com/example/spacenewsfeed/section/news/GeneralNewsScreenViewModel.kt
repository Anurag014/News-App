package com.example.spacenewsfeed.section.news

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

sealed interface GeneralNewsUiState {
    data class Success(val generalNewsList: NewsArticleList): GeneralNewsUiState
    object Error : GeneralNewsUiState
    object Loading : GeneralNewsUiState
}

class GeneralNewsScreenViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    var generalNewsUiState: GeneralNewsUiState by mutableStateOf(GeneralNewsUiState.Loading)
        private set

    init {
        getGeneralNewsList()
    }

    fun getGeneralNewsList() {
        viewModelScope.launch {
            generalNewsUiState = try {
                GeneralNewsUiState.Success(newsRepository.getGeneralNews())
            } catch (e: IOException) {
                GeneralNewsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val newsRepository = application.container.newsRepository
                GeneralNewsScreenViewModel(newsRepository = newsRepository)
            }
        }
    }
}