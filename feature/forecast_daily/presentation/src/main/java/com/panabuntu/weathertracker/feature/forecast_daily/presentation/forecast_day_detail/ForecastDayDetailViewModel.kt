package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panabuntu.weathertracker.core.domain.result.onError
import com.panabuntu.weathertracker.core.domain.result.onSuccess
import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.core.presentation.util.navArgs
import com.panabuntu.weathertracker.feature.forecast_daily.usecase.GetDayForecastDetailUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ForecastDayDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getDayForecastDetailUseCase: GetDayForecastDetailUseCase,
    private val logger: AppLogger
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<ForecastDayDetailRoute>()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ForecastDayDetailState())
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
            initialValue = ForecastDayDetailState()
        )

    private var getDayDetailJob: Job? = null


    fun onIntent(intent: ForecastDayDetailIntent) {
        logger.d("onIntent() called with: intent = [$intent]")
        viewModelScope.launch {
            when (intent) {
                ForecastDayDetailIntent.GetDayDetail -> getDayDetail()
            }
        }
    }

    private fun getDayDetail() {
        getDayDetailJob?.cancel()

        getDayDetailJob = viewModelScope.launch {
            getDayForecastDetailUseCase(
                date = navArgs.date,
                lat = navArgs.lat,
                lon = navArgs.lon
            ).collectLatest {
                it.onError { }
                it.onSuccess {
                    it
                }
            }
        }
    }
}