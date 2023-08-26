package com.example.spacenewsfeed.section.spacenews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spacenewsfeed.NewsApplication
import com.example.spacenewsfeed.models.PaginatedArticleList
import com.example.spacenewsfeed.repository.SpaceNewsRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ArticleUiState {
    data class Success(val paginatedArticleList: PaginatedArticleList): ArticleUiState
    object Error : ArticleUiState
    object Loading : ArticleUiState
}

class ArticleScreenViewModel(
    private val spaceNewsRepository: SpaceNewsRepository
): ViewModel() {

    var articleUiState: ArticleUiState by mutableStateOf(ArticleUiState.Loading)
        private set

    init {
        getPaginatedArticleList()
    }

    fun getPaginatedArticleList() {
        viewModelScope.launch {
            articleUiState = try {
                ArticleUiState.Success(spaceNewsRepository.getPaginatedArticleList())
            } catch (e: IOException) {
                ArticleUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NewsApplication)
                val spaceNewsRepository = application.container.spaceNewsRepository
                ArticleScreenViewModel(spaceNewsRepository = spaceNewsRepository)
            }
        }
    }
}