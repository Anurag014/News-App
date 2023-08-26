package com.example.spacenewsfeed.section.spacenews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spacenewsfeed.utils.SpaceBottomNavigationType


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SpaceFlightNewsContent() {
    val bottomNavigationItemContentList = listOf(
        BottomNavigationItemContent(
            spaceBottomNavigationType = SpaceBottomNavigationType.Article,
            icon = Icons.Filled.Article,
            title = "Article"
        ),
        BottomNavigationItemContent(
            spaceBottomNavigationType = SpaceBottomNavigationType.Report,
            icon = Icons.Filled.Description,
            title = "Report"
        ),
        BottomNavigationItemContent(
            spaceBottomNavigationType = SpaceBottomNavigationType.Blog,
            icon = Icons.Filled.StickyNote2,
            title = "Blog"
        )
    )
    val articleScreenViewModel: ArticleScreenViewModel = viewModel(factory = ArticleScreenViewModel.Factory)
    val reportScreenViewModel: ReportScreenViewModel = viewModel(factory = ReportScreenViewModel.Factory)
    val blogScreenViewModel: BlogScreenViewModel = viewModel(factory = BlogScreenViewModel.Factory)
    var tab by remember { mutableStateOf(bottomNavigationItemContentList[0]) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when (tab.spaceBottomNavigationType) {
            SpaceBottomNavigationType.Article -> {
                ArticleScreen(
                    articleUiState = articleScreenViewModel.articleUiState,
                    modifier = Modifier.weight(0.9f),
                    retryAction = articleScreenViewModel::getPaginatedArticleList
                )
            }
            SpaceBottomNavigationType.Blog -> {
                BlogScreen(
                    blogUiState = blogScreenViewModel.blogUiState,
                    modifier = Modifier.weight(0.9f),
                    retryAction = blogScreenViewModel::getPaginatedBlogList
                )
            }
            SpaceBottomNavigationType.Report -> {
                ReportScreen(
                    reportUiState = reportScreenViewModel.reportUiState,
                    modifier = Modifier.weight(0.9f),
                    retryAction = reportScreenViewModel::getPaginatedReportList
                )
            }
        }
        SpaceBottomNavigationBar(
            currentTab = tab,
            onTabPressed = { tab = it },
            navigationItemContentList = bottomNavigationItemContentList,
            modifier = Modifier.weight(0.1f)
        )
    }
}

@Composable
private fun SpaceBottomNavigationBar(
    currentTab: BottomNavigationItemContent,
    onTabPressed: ((BottomNavigationItemContent) -> Unit),
    navigationItemContentList: List<BottomNavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier.defaultMinSize(minHeight = 50.dp)) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem,
                onClick = { onTabPressed(navItem) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(text = navItem.title)
                }
            )
        }
    }
}


private data class BottomNavigationItemContent(
    val spaceBottomNavigationType: SpaceBottomNavigationType,
    val icon: ImageVector,
    val title: String
)



