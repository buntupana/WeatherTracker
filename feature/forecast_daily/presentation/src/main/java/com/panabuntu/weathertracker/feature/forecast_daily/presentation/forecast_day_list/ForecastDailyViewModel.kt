package com.panabuntu.weathertracker.feature.forecast_day_list.presentation.forecast_day_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.domain.result.onError
import com.panabuntu.weathertracker.core.domain.result.onSuccess
import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.core.presentation.snackbar.SnackbarController
import com.panabuntu.weathertracker.core.presentation.snackbar.SnackbarEvent
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.core.usecase.GetDayForecastListUseCase
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.mapper.toDayForecastItem
import com.panabuntu.weathertracker.forecast_list.presentation.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForecastDailyViewModel(
    private val logger: AppLogger,
    private val getDayForecastListUseCase: GetDayForecastListUseCase
) : ViewModel() {

    private val locationName = Const.DEFAULT_LOCATION_NAME
    private val latitude = Const.DEFAULT_LAT
    private val longitude = Const.DEFAULT_LON

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(
        ForecastDailyState(
            locationName = locationName,
            lat = latitude,
            lon = longitude
        )
    )
    var state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                onIntent(ForecastDailyIntent.GetDailyForecast)
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ForecastDailyState(
                locationName = locationName,
                lat = latitude,
                lon = longitude
            )
        )

    private var getDailyForecastJob: Job? = null

    fun onIntent(intent: ForecastDailyIntent) {
        logger.d("onIntent() called with: intent = [$intent]")
        viewModelScope.launch {
            when (intent) {
                ForecastDailyIntent.GetDailyForecast -> getDailyForecast()
                else -> {}
            }
        }
    }

    private fun getDailyForecast() {

        getDailyForecastJob?.cancel()
        getDailyForecastJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = state.value.dailyList.isEmpty(),
                    isRefreshing = state.value.dailyList.isNotEmpty()
                )
            }

            getDayForecastListUseCase(
                lat = latitude,
                lon = longitude
            ).collectLatest { result ->
                result.onError {
                    _state.update {
                        it.copy(isLoading = false, isRefreshing = false)
                    }
                    if (state.value.dailyList.isNotEmpty()) {
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = UiText.StringResource(
                                    resId = R.string.core_error_refreshing_data
                                )
                            )
                        )
                    }
                }
                result.onSuccess { dailyList ->
                    _state.update {
                        it.copy(
                            dailyList = dailyList.toDayForecastItem(),
                            isLoading = false,
                            isRefreshing = false
                        )
                    }
                }
            }
        }
    }
}