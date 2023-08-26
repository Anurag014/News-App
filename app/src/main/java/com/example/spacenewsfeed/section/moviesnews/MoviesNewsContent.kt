package com.example.spacenewsfeed.section.moviesnews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoviesNewsContent() {

    val moviesScreenViewModel: MoviesScreenViewModel = viewModel(factory = MoviesScreenViewModel.Factory)

    MoviesScreen(
        moviesUiState = moviesScreenViewModel.moviesUiState,
        retryAction = moviesScreenViewModel::getMoviesNewsList
    )
}