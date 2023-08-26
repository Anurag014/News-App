package com.example.spacenewsfeed.section.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchNewsContent(
    text: String,
    searchScreenViewModel: SearchScreenViewModel
) {
    SearchScreen(
        searchUiState = searchScreenViewModel.searchUiState,
        retryAction = { searchScreenViewModel.searchNewsList(text) }
    )
}