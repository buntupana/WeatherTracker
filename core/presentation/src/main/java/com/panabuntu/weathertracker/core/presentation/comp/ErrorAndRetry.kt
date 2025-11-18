package com.panabuntu.weathertracker.core.presentation.comp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panabuntu.weathertracker.core.presentation.R
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.theme.LocalAppDimens
import com.panabuntu.weathertracker.core.presentation.util.balanced
import com.panabuntu.weathertracker.core.presentation.util.getOnBackgroundColor

@Composable
fun ErrorAndRetry(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    errorMessage: String,
    onRetryClick: () -> Unit
) {

    val dimens = LocalAppDimens.current


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            color = contentColor,
            textAlign = TextAlign.Center,
            style = LocalTextStyle.current.balanced()
        )

        Spacer(modifier = Modifier.padding(vertical = dimens.paddingLarge.dp))

        Button(
            onClick = onRetryClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = contentColor,
                contentColor = contentColor.getOnBackgroundColor()
            )
        ) {
            Text(
                text = stringResource(id = R.string.core_retry),
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
private fun ErrorAndRetryPreview() {
    AppTheme {
        ErrorAndRetry(
            modifier = Modifier
                .background(Color.White),
            contentColor = Color.Black,
            errorMessage = "Unknown Error",
            onRetryClick = {}
        )
    }
}