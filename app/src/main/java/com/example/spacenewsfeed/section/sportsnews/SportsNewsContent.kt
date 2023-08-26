package com.example.spacenewsfeed.section.sportsnews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SportsNewsContent() {

    val sportsScreenViewModel: SportsScreenViewModel = viewModel(factory = SportsScreenViewModel.Factory)

    SportsScreen(
        sportsUiState = sportsScreenViewModel.sportsUiState,
        retryAction = sportsScreenViewModel::getSportsNewsList
    )
}