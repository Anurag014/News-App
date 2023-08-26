package com.example.spacenewsfeed.section.spacenews

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.spacenewsfeed.R
import com.example.spacenewsfeed.models.Article
import com.example.spacenewsfeed.models.PaginatedArticleList
import com.example.spacenewsfeed.ui.theme.Link
import com.example.spacenewsfeed.utils.DisplaySpaceCard
import com.example.spacenewsfeed.utils.ErrorScreen
import com.example.spacenewsfeed.utils.LoadingScreen
import com.example.spacenewsfeed.utils.formatDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleScreen(
    articleUiState: ArticleUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (articleUiState) {
        is ArticleUiState.Loading -> LoadingScreen(modifier = modifier)
        is ArticleUiState.Success -> ArticleScreenContent(articleUiState.paginatedArticleList, modifier = modifier)
        is ArticleUiState.Error -> ErrorScreen(modifier = modifier, onRetryClicked = retryAction)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleScreenContent(
    paginatedArticleList: PaginatedArticleList,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        LazyColumn(
        ) {
            items(items = paginatedArticleList.results) { article ->
                DisplaySpaceCard(spaceNewsDetail = article, modifier = Modifier.padding(10.dp))
            }
        }
    }
}

