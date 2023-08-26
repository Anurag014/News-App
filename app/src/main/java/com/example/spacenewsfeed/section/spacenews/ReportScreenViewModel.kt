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
import com.example.spacenewsfeed.models.PaginatedReportList
import com.example.spacenewsfeed.repository.SpaceNewsRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ReportUiState {
    data class Success(val paginatedReportList: PaginatedReportList): ReportUiState
    object Error : ReportUiState
    object Loading : ReportUiState
}

class ReportScreenViewModel(
    private val spaceNewsRepository: SpaceNewsRepository
): ViewModel() {

    var reportUiState: ReportUiState by mutableStateOf(ReportUiState.Loading)
        private set

    init {
        getPaginatedReportList()
    }

    fun getPaginatedReportList() {
        viewModelScope.launch {
            reportUiState = try {
                ReportUiState.Success(spaceNewsRepository.getPaginatedReportList())
            } catch (e: IOException) {
                ReportUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val spaceNewsRepository = application.container.spaceNewsRepository
                ReportScreenViewModel(spaceNewsRepository = spaceNewsRepository)
            }
        }
    }
}