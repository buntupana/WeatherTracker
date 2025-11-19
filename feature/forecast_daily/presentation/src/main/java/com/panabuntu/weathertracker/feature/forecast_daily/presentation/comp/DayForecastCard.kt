package com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panabuntu.weathertracker.core.presentation.comp.ImageFromUrl
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.theme.LocalAppDimens
import com.panabuntu.weathertracker.feature.forecast_daily.model.DayForecastSimple
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.mapper.toViewEntity
import com.panabuntu.weathertracker.forecast_list.presentation.R
import java.time.LocalDate

@Composable
fun DayForecastCard(
    modifier: Modifier = Modifier,
    item: DayForecastEntityView,
    onClick: () -> Unit
) {
    val dimens = LocalAppDimens.current

    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.paddingLarge.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ImageFromUrl(
                modifier = Modifier.size(dimens.iconForecastList.dp),
                imageUrl = item.iconUrl
            )

            Column(
                modifier = Modifier.padding(start = dimens.paddingLarge.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        style = MaterialTheme.typography.headlineSmall,
                        text = item.dayName.asString(),
                    )

                    Text(
                        text = buildAnnotatedString {
                            append(item.dayOfMonth.toString())
                            append(" ")
                            append(item.monthName.asString())
                        }
                    )
                }

                Text(
                    text = item.description.orEmpty()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(R.string.forecast_daily_temp_min, item.minTemp)
                    )

                    Spacer(
                        modifier = Modifier.width(dimens.paddingLarge.dp)
                    )

                    Text(
                        text = stringResource(R.string.forecast_daily_temp_max, item.maxTemp)
                    )
                }
            }
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
    showBackground = true
)
@Composable
private fun DayForecastCardPreview() {

    val viewEntity = DayForecastSimple(
        date = LocalDate.now(),
        timestamp = 0,
        maxTemp = 38.3f,
        minTemp = 10.8f,
        description = "overcast clouds",
        iconUrl = ""
    ).toViewEntity()

    AppTheme {
        DayForecastCard(
            modifier = Modifier.fillMaxWidth(),
            item = viewEntity,
            onClick = {}
        )
    }
}