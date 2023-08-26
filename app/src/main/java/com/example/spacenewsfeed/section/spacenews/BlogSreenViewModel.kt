package com.example.spacenewsfeed.section.spacenews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spacenewsfeed.NewsApplication
import com.example.spacenewsfeed.models.PaginatedBlogList
import com.example.spacenewsfeed.repository.SpaceNewsRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BlogUiState {
    data class Success(val paginatedBlogList: PaginatedBlogList): BlogUiState
    object Error : BlogUiState
    object Loading : BlogUiState
}

class BlogScreenViewModel(
    private val spaceNewsRepository: SpaceNewsRepository
): ViewModel() {

    var blogUiState: BlogUiState by mutableStateOf(BlogUiState.Loading)
        private set

    init {
        getPaginatedBlogList()
    }

    fun getPaginatedBlogList() {
        viewModelScope.launch {
            blogUiState = try {
                BlogUiState.Success(spaceNewsRepository.getPaginatedBlogList())
            } catch (e: IOException) {
                BlogUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val spaceNewsRepository = application.container.spaceNewsRepository
                BlogScreenViewModel(spaceNewsRepository = spaceNewsRepository)
            }
        }
    }
}