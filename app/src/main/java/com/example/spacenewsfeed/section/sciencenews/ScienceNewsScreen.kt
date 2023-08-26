package com.example.spacenewsfeed.section.sciencenews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spacenewsfeed.models.NewsArticleList
import com.example.spacenewsfeed.utils.DisplayNewsCard
import com.example.spacenewsfeed.utils.ErrorScreen
import com.example.spacenewsfeed.utils.LoadingScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScienceNewsScreen(
    scienceNewsUiState: ScienceNewsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (scienceNewsUiState) {
        is ScienceNewsUiState.Loading -> LoadingScreen(modifier = modifier)
        is ScienceNewsUiState.Success -> ScienceNewsScreenContent(newsArticleList = scienceNewsUiState.scienceNewsList, modifier = modifier)
        is ScienceNewsUiState.Error -> ErrorScreen(modifier = modifier, onRetryClicked = retryAction)
    }
}


@Composable
fun ScienceNewsScreenContent(
    newsArticleList: NewsArticleList,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        LazyColumn() {
            items(newsArticleList.articles) { techNews ->
                DisplayNewsCard(newsDetail = techNews, modifier = Modifier.padding(10.dp))
            }
        }
    }
}