package com.panabuntu.weathertracker.feature.core.presentation.comp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.presentation.R
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.theme.LocalAppDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastDailyTopBar(
    modifier: Modifier = Modifier,
    locationName: String
) {

    val dimens = LocalAppDimens.current

    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier.size(dimens.iconTopBar.dp),
                        painter = painterResource(R.drawable.ic_app),
                        contentDescription = null,
                    )

                    Spacer(
                        modifier = Modifier.size(dimens.paddingLarge.dp)
                    )

                    Text(text = "WeatherTracker")
                }

                Text(
                    modifier = Modifier.padding(horizontal = dimens.paddingLarge.dp),
                    text = locationName
                )

                Spacer(modifier = Modifier.height(dimens.paddingLarge.dp))
            }
        },
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun ForecastDailyTopBarPreview() {
    AppTheme {
        ForecastDailyTopBar(
            locationName = Const.DEFAULT_LOCATION_NAME
        )
    }
}