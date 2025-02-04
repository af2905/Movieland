package com.github.af2905.movieland.compose.components.bottomsheet

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppModalBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(sheetState) {
        snapshotFlow { sheetState.currentValue }.collectIndexed { _, value ->
            when (value) {
                SheetValue.Hidden -> {
                    focusManager.clearFocus()
                }

                SheetValue.Expanded, SheetValue.PartiallyExpanded -> {}
            }
        }
    }
    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismissRequest()
                }
            }
        },
        sheetState = sheetState,
        content = { content() },
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.onSurfaceVariant) }
    )
}