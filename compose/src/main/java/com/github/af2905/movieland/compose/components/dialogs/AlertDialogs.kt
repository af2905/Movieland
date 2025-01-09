package com.github.af2905.movieland.compose.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.af2905.movieland.compose.components.buttons.FlatAlertButtonView
import com.github.af2905.movieland.compose.components.buttons.FlatButtonView
import com.github.af2905.movieland.compose.theme.AppTheme

private const val W360 = 360
private const val W600 = 600

@Composable
fun AppAlertDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    text: String? = null,
    actionPositiveText: String? = null,
    actionNegativeText: String? = null,
    isPositiveActionAlert: Boolean = false,
    onPositiveClick: () -> Unit = {},
    onNegativeClick: () -> Unit = {},
    onDismissRequest: () -> Unit
) {
    val dialogWidth: Dp = when (val screenWidth = LocalConfiguration.current.screenWidthDp) {
        in W360..W600 -> AppTheme.dimens.alertDialogWidth
        else -> Dp(AppTheme.dimens.alertDialogWidthProportion * screenWidth)
    }
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = modifier
                .width(dialogWidth)
                .background(
                    color = AppTheme.colors.theme.tintCard,
                    shape = RoundedCornerShape(AppTheme.dimens.radiusDialog)
                )
                .padding(
                    top = AppTheme.dimens.spaceL,
                    bottom = AppTheme.dimens.spaceXS
                )
        ) {
            if (!title.isNullOrEmpty()) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(
                            start = AppTheme.dimens.spaceXL,
                            end = AppTheme.dimens.spaceXL,
                            bottom = AppTheme.dimens.spaceXS
                        ),
                    style = AppTheme.typography.title3
                )
            }
            if (!text.isNullOrEmpty()) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(
                            start = AppTheme.dimens.spaceXL,
                            end = AppTheme.dimens.spaceXL,
                            bottom = AppTheme.dimens.spaceXS
                        ),
                    color = AppTheme.colors.type.secondary,
                    style = AppTheme.typography.body
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.spaceXS),
                horizontalArrangement = Arrangement
                    .spacedBy(AppTheme.dimens.spaceXS, Alignment.End),
            ) {
                if (!actionNegativeText.isNullOrEmpty()) {
                    FlatButtonView(
                        text = actionNegativeText,
                        enabled = true
                    ) {
                        onNegativeClick()
                        onDismissRequest()
                    }
                }
                if (!actionPositiveText.isNullOrEmpty() && !isPositiveActionAlert) {
                    FlatButtonView(
                        text = actionPositiveText,
                        enabled = true,
                    ) {
                        onPositiveClick()
                        onDismissRequest()
                    }
                }
                if (!actionPositiveText.isNullOrEmpty() && isPositiveActionAlert) {
                    FlatAlertButtonView(
                        text = actionPositiveText,
                        enabled = true
                    ) {
                        onPositiveClick()
                        onDismissRequest()
                    }
                }
            }
        }
    }
}

/*Previews*/

@Preview(showBackground = true)
@Composable
fun PreviewAppAlertDialogWithTitleAndText() {
    AppAlertDialog(
        title = "Alert Title",
        text = "This is the description text of the alert dialog.",
        actionPositiveText = "OK",
        actionNegativeText = "Cancel",
        onPositiveClick = { /* Handle Positive Click */ },
        onNegativeClick = { /* Handle Negative Click */ },
        onDismissRequest = { /* Handle Dismiss */ }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAppAlertDialogWithoutTitle() {
    AppAlertDialog(
        text = "This is the description text of the alert dialog.",
        actionPositiveText = "Accept",
        actionNegativeText = "Decline",
        onPositiveClick = { /* Handle Positive Click */ },
        onNegativeClick = { /* Handle Negative Click */ },
        onDismissRequest = { /* Handle Dismiss */ }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAppAlertDialogPositiveActionAlert() {
    AppAlertDialog(
        title = "Alert Title",
        text = "This is the description text of the alert dialog.",
        actionPositiveText = "Confirm",
        isPositiveActionAlert = true,
        onPositiveClick = { /* Handle Positive Click */ },
        onDismissRequest = { /* Handle Dismiss */ }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAppAlertDialogNegativeOnly() {
    AppAlertDialog(
        title = "Alert Title",
        text = "This is the description text of the alert dialog.",
        actionNegativeText = "Dismiss",
        onNegativeClick = { /* Handle Negative Click */ },
        onDismissRequest = { /* Handle Dismiss */ }
    )
}
