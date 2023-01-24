package com.github.af2905.movieland.detail.persondetail.compose

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailContract
import com.github.af2905.movieland.detail.persondetail.presentation.PersonDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun PersonDetailScreen(personDetailViewModel: PersonDetailViewModel) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val viewState = personDetailViewModel.container.state.collectAsState()

    when (val state = viewState.value) {
        is PersonDetailContract.State.Loading -> PersonDetailLoading()
        is PersonDetailContract.State.Content -> PersonDetailContent(
            scope = scope,
            scaffoldState = scaffoldState,
            state = state
        )
        is PersonDetailContract.State.Error -> {
            Unit
        }
    }
}

@Composable
fun PersonDetailLoading() {
    LoadingPersonDetailShimmer(imageHeight = 250.dp)
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun PersonDetailContent(
    scope: CoroutineScope,
    scaffoldState: BackdropScaffoldState,
    state: PersonDetailContract.State.Content
) {

    LaunchedEffect(scaffoldState) { scaffoldState.reveal() }

    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            TopAppBar(
                title = { state.personDetailItem.name?.let { Text(it) } },
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
        backLayerContent = { PersonItem(state.personDetailItem) },
        frontLayerContent = { PersonBiography(state.personDetailItem) }
    )
}
