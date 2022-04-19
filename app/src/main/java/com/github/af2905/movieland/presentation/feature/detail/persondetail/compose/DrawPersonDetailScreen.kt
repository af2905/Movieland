package com.github.af2905.movieland.presentation.feature.detail.persondetail.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.presentation.feature.detail.persondetail.PersonDetailContract
import com.github.af2905.movieland.presentation.feature.detail.persondetail.PersonDetailViewModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun DrawPersonDetailScreen(personDetailViewModel: PersonDetailViewModel) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val viewState = personDetailViewModel.container.state.collectAsState()
    val title = remember { mutableStateOf("") }

    val isLoadingState = viewState.value is PersonDetailContract.State.Loading
    val isContentState = viewState.value is PersonDetailContract.State.Content
    val isErrorState = viewState.value is PersonDetailContract.State.Error

    LaunchedEffect(scaffoldState) { scaffoldState.reveal() }

    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            TopAppBar(
                title = { Text(title.value) },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { } }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {

                                scaffoldState.snackbarHostState.showSnackbar("Like clicked")
                            }
                        }
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = null)
                    }
                },
                elevation = 0.dp,
                backgroundColor = Color.Transparent
            )
        },
        backLayerContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                when (val state = viewState.value) {
                    is PersonDetailContract.State.Loading -> LoadingPersonDetailShimmer(imageHeight = 500.dp)
                    is PersonDetailContract.State.Content -> {
                        title.value = state.personItem.name
                        PersonItem(item = state.personItem)
                    }
                    is PersonDetailContract.State.Error -> {}
                }
            }
        },
        frontLayerContent = {
            if (isContentState) {
                PersonBiography((viewState.value as PersonDetailContract.State.Content).personItem)
            }
        }
    )
}

/*@Composable
@OptIn(ExperimentalMaterialApi::class)
fun DrawPersonDetailScreen(personDetailViewModel: PersonDetailViewModel) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val viewState = personDetailViewModel.container.state.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { } }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    personDetailViewModel.personDetailClickListener
                                    scaffoldState.snackbarHostState
                                        .showSnackbar("Like clicked")
                                }
                            }
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = null)
                        }
                    },
                    elevation = 0.dp,
                    backgroundColor = Color.Transparent
                )
            }
        }
    ) {
        when (val state = viewState.value) {
            is PersonDetailContract.State.Loading -> {}
            is PersonDetailContract.State.Content -> DrawPersonItem(item = state.personItem)

            is PersonDetailContract.State.Error -> {}
        }
    }
}*/
