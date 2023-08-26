package com.example.spacenewsfeed.section.spacenews

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.spacenewsfeed.models.PaginatedReportList
import com.example.spacenewsfeed.models.Report
import com.example.spacenewsfeed.ui.theme.Link
import com.example.spacenewsfeed.utils.ErrorScreen
import com.example.spacenewsfeed.utils.LoadingScreen
import com.example.spacenewsfeed.utils.formatDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportScreen(
    reportUiState: ReportUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (reportUiState) {
        is ReportUiState.Loading -> LoadingScreen(modifier = modifier)
        is ReportUiState.Success -> ReportScreenContent(reportUiState.paginatedReportList, modifier = modifier)
        is ReportUiState.Error -> ErrorScreen(modifier = modifier, onRetryClicked = retryAction)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportScreenContent(
    paginatedReportList: PaginatedReportList,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        LazyColumn(
        ) {
            items(items = paginatedReportList.results) { report ->
                ReportContentCard(reportDetail = report, modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportContentCard(
    reportDetail: Report,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isDescriptionOpen = remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .clickable {
                val urlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(reportDetail.url)
                )
                context.startActivity(urlIntent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.onPrimary, containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Column() {
            Text(text = "Source: ${reportDetail.newsSite}", modifier = Modifier.padding( top = 10.dp, start = 10.dp, end = 10.dp))
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(reportDetail.imageUrl)
                    .crossfade(true)
                    .build(),
                loading = { Image(painter = painterResource(id = R.drawable.loading_img), contentDescription = null) },
                error = { Image(painter = painterResource(id = R.drawable.ic_broken_image), contentDescription = null) },
                contentDescription = "Article Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(175.dp)
            )
            Column() {
                Text(
                    text = reportDetail.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                AnimatedVisibility(visible = isDescriptionOpen.value) {
                    Text(
                        text = reportDetail.summary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, bottom = 10.dp, end = 10.dp),
                        fontSize = 14.sp
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 10.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = formatDateTime(reportDetail.updatedAt), modifier = Modifier.weight(0.7f))
                    IconButton(onClick = { isDescriptionOpen.value = !isDescriptionOpen.value}) {
                        if (isDescriptionOpen.value) {
                            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
                        } else {
                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}