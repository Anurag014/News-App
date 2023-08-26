package com.example.spacenewsfeed.utils

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.spacenewsfeed.R
import com.example.spacenewsfeed.models.Article
import com.example.spacenewsfeed.models.Blog
import com.example.spacenewsfeed.models.NewsArticle
import com.example.spacenewsfeed.models.NewsArticleList

@SuppressLint("NewApi")
@Composable
fun DisplaySpaceCard(
    spaceNewsDetail: Article,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isDescriptionOpen = remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .clickable {
                val urlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(spaceNewsDetail.url)
                )
                context.startActivity(urlIntent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.onPrimary, containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Column() {
            Text(text = "Source: ${spaceNewsDetail.newsSite}", modifier = Modifier.padding( top = 10.dp, start = 10.dp, end = 10.dp))
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(spaceNewsDetail.imageUrl)
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
                    text = spaceNewsDetail.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                AnimatedVisibility(visible = isDescriptionOpen.value) {
                    Text(
                        text = spaceNewsDetail.summary,
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
                    Text(text = formatDateTime(spaceNewsDetail.updatedAt), modifier = Modifier.weight(0.7f))
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

@SuppressLint("NewApi")
@Composable
fun DisplayNewsCard(
    newsDetail: NewsArticle,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isDescriptionOpen = remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .clickable {
                val urlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(newsDetail.url)
                )
                context.startActivity(urlIntent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.onPrimary, containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Column() {
            Text(text = "Source: ${newsDetail.source.name}", modifier = Modifier.padding( top = 10.dp, start = 10.dp, end = 10.dp))
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(newsDetail.urlToImage)
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
                    text = newsDetail.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                AnimatedVisibility(visible = isDescriptionOpen.value) {
                    newsDetail.description?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, bottom = 10.dp, end = 10.dp),
                            fontSize = 14.sp
                        )
                    }
                }
                Divider(modifier = Modifier.padding(horizontal = 10.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = newsFormatDateTime(newsDetail.publishedAt), modifier = Modifier.weight(0.7f))
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