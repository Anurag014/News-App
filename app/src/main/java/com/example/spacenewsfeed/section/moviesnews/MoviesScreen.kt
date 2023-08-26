package com.example.spacenewsfeed.section.moviesnews

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.spacenewsfeed.R
import com.example.spacenewsfeed.models.NewsArticle
import com.example.spacenewsfeed.models.NewsArticleList
import com.example.spacenewsfeed.section.sportsnews.SportsUiState
import com.example.spacenewsfeed.ui.theme.Link
import com.example.spacenewsfeed.utils.DisplayNewsCard
import com.example.spacenewsfeed.utils.ErrorScreen
import com.example.spacenewsfeed.utils.LoadingScreen
import com.example.spacenewsfeed.utils.newsFormatDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoviesScreen(
    moviesUiState: MoviesUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {

    when (moviesUiState) {
        is MoviesUiState.Loading -> LoadingScreen(modifier = modifier)
        is MoviesUiState.Success -> MoviesScreenContent(moviesUiState.moviesNewsList, modifier = modifier)
        is MoviesUiState.Error -> ErrorScreen(modifier = modifier, onRetryClicked = retryAction)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoviesScreenContent(
    newsArticleList: NewsArticleList,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        LazyColumn() {
            items(newsArticleList.articles) { moviesNews ->
                DisplayNewsCard(newsDetail = moviesNews, modifier = Modifier.padding(10.dp))
            }
        }
    }
}

