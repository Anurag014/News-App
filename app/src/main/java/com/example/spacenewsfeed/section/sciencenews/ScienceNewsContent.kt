package com.example.spacenewsfeed.section.sciencenews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScienceNewsContent() {

    val scienceNewsScreenViewModel: ScienceNewsScreenViewModel = viewModel(factory = ScienceNewsScreenViewModel.Factory)

    ScienceNewsScreen(
        scienceNewsUiState = scienceNewsScreenViewModel.scienceNewsUiState,
        retryAction = scienceNewsScreenViewModel::getScienceNewsList
    )
}