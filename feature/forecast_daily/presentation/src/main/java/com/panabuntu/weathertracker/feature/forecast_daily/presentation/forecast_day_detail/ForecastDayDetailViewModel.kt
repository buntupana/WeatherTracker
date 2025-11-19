package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panabuntu.weathertracker.core.domain.result.onError
import com.panabuntu.weathertracker.core.domain.result.onSuccess
import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.core.presentation.R
import com.panabuntu.weathertracker.core.presentation.snackbar.SnackbarController
import com.panabuntu.weathertracker.core.presentation.snackbar.SnackbarEvent
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.core.presentation.util.navArgs
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.mapper.toForecastDetailInfo
import com.panabuntu.weathertracker.feature.forecast_daily.usecase.GetDayForecastDetailUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForecastDayDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getDayForecastDetailUseCase: GetDayForecastDetailUseCase,
    private val logger: AppLogger
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<ForecastDayDetailRoute>()

    private var hasLoadedInitialData = false
    private var isDataLoadedSuccessfully = false

    private val _state =
        MutableStateFlow(ForecastDayDetailState(locationName = navArgs.locationName))
    var state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                onIntent(ForecastDayDetailIntent.GetDayDetail)
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ForecastDayDetailState(locationName = navArgs.locationName)
        )

    private var getDayDetailJob: Job? = null

    fun onIntent(intent: ForecastDayDetailIntent) {
        logger.d("onIntent() called with: intent = [$intent]")
        viewModelScope.launch {
            when (intent) {
                ForecastDayDetailIntent.GetDayDetail -> getDayDetail()
                else -> {}
            }
        }
    }

    private fun getDayDetail() {
        getDayDetailJob?.cancel()

        _state.update {
            it.copy(
                isLoading = isDataLoadedSuccessfully.not(),
                isRefreshing = isDataLoadedSuccessfully
            )
        }

        getDayDetailJob = viewModelScope.launch {
            getDayForecastDetailUseCase(
                date = navArgs.date,
                lat = navArgs.lat,
                lon = navArgs.lon
            ).collectLatest { result ->
                result.onError {
                    if (isDataLoadedSuccessfully) {
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = UiText.StringResource(
                                    resId = R.string.core_error_refreshing_data
                                )
                            )
                        )
                        _state.update { it.copy(isLoading = false, isRefreshing = false) }
                    } else {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                errorMessage = UiText.StringResource(R.string.core_error_loading_data)
                            )
                        }
                    }
                }
                result.onSuccess { result ->
                    if (result == null) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                errorMessage = UiText.StringResource(R.string.core_error_loading_data)
                            )
                        }
                    } else {
                        isDataLoadedSuccessfully = true
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                forecastDetailInfo = result.toForecastDetailInfo()
                            )
                        }
                    }
                }
            }
        }
    }
}