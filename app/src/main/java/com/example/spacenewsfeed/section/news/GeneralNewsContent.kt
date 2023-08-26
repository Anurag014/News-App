package com.example.spacenewsfeed.section.news

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GeneralNewsContent() {

    val generalNewsScreenViewModel: GeneralNewsScreenViewModel = viewModel(factory = GeneralNewsScreenViewModel.Factory)

    GeneralNewsScreen(
        generalNewsUiState = generalNewsScreenViewModel.generalNewsUiState,
        retryAction = generalNewsScreenViewModel::getGeneralNewsList
    )
}