package com.example.spacenewsfeed

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spacenewsfeed.section.moviesnews.MoviesNewsContent
import com.example.spacenewsfeed.section.news.GeneralNewsContent
import com.example.spacenewsfeed.section.sciencenews.ScienceNewsContent
import com.example.spacenewsfeed.section.search.SearchNewsContent
import com.example.spacenewsfeed.section.search.SearchScreenViewModel
import com.example.spacenewsfeed.section.spacenews.SpaceFlightNewsContent
import com.example.spacenewsfeed.section.sportsnews.SportsNewsContent
import com.example.spacenewsfeed.utils.MainNavigationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NewsApp() {
    var searchText by remember { mutableStateOf("") }
    val searchScreenViewModel: SearchScreenViewModel = viewModel(factory = SearchScreenViewModel.Factory)
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var isSearchClicked by remember { mutableStateOf(false) }
    val mainNavigationItemContentList = listOf(
        MainNavigationItemContent(
            mainNavigationType = MainNavigationType.Space,
            icon = Icons.Default.RocketLaunch,
            title = "Space FLight News"
        ),
        MainNavigationItemContent(
            mainNavigationType = MainNavigationType.Sports,
            icon = Icons.Default.Sports,
            title = "Sports News"
        ),
        MainNavigationItemContent(
            mainNavigationType = MainNavigationType.News,
            icon = Icons.Default.Feed,
            title = "Current Affairs"
        ),
        MainNavigationItemContent(
            mainNavigationType = MainNavigationType.Movies,
            icon = Icons.Default.Movie,
            title = "Movies News"
        ),
        MainNavigationItemContent(
            mainNavigationType = MainNavigationType.Technology,
            icon = Icons.Default.Science,
            title = "Science News"
        )
    )
    val selectedItem = remember { mutableStateOf(mainNavigationItemContentList[0]) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            onMenuIconClicked = { scope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() } },
            onSearchClicked = {
                isSearchClicked = true
                scope.launch { drawerState.close() }
            },
            onBackClicked = { isSearchClicked = false },
            isSearchSelected = isSearchClicked
        )
        AnimatedVisibility(visible = isSearchClicked) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { newSearchText ->
                    searchText = newSearchText
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        searchScreenViewModel.searchNewsList(searchText)
                        keyboardController?.hide()
                    }
                ),
                textStyle = TextStyle(fontSize = 18.sp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text(text = "Search")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    placeholderColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
        NewsNavigationDrawer(
            drawerState = drawerState,
            scope = scope,
            mainNavigationList = mainNavigationItemContentList,
            onMenuIconClicked = { selectedItem.value = it },
            selectedItem = selectedItem.value,
            isSearchSelected = isSearchClicked
        ) {
            if (isSearchClicked) {
                SearchNewsContent(text = searchText, searchScreenViewModel = searchScreenViewModel)
            } else {
                when (selectedItem.value.mainNavigationType) {
                    MainNavigationType.Space -> {
                        SpaceFlightNewsContent()
                    }

                    MainNavigationType.News -> {
                        GeneralNewsContent()
                    }

                    MainNavigationType.Movies -> {
                        MoviesNewsContent()
                    }

                    MainNavigationType.Sports -> {
                        SportsNewsContent()
                    }
                    MainNavigationType.Technology -> {
                        ScienceNewsContent()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigationDrawer(
    drawerState: DrawerState,
    isSearchSelected: Boolean,
    scope: CoroutineScope,
    mainNavigationList: List<MainNavigationItemContent>,
    selectedItem: MainNavigationItemContent,
    onMenuIconClicked: (MainNavigationItemContent) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = !isSearchSelected,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                mainNavigationList.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = item.title)},
                        label = { Text(text = item.title) },
                        selected = item == selectedItem,
                        onClick = {
                            onMenuIconClicked(item)
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClicked: () -> Unit,
    onMenuIconClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    isSearchSelected: Boolean,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "News Feed",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier,
        actions = {
            IconButton(onClick =  onSearchClicked ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
            }
        },
        navigationIcon = {
            if (isSearchSelected) {
                IconButton(onClick =  onBackClicked ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Icon")
                }
            } else {
                IconButton(onClick =  onMenuIconClicked ) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu Icon")
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    )
}

data class MainNavigationItemContent(
    val mainNavigationType: MainNavigationType,
    val icon: ImageVector,
    val title: String
)