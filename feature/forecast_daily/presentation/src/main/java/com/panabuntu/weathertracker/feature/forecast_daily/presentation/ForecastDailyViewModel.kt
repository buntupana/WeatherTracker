package com.panabuntu.weathertracker.feature.forecast_daily.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.feature.forecast_daily.repository.usecase.GetDailyForecastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ForecastDailyViewModel(
    private val logger: AppLogger,
    private val getDailyForecastUseCase: GetDailyForecastUseCase
) : ViewModel() {

    val _state = MutableStateFlow(ForecastDailyState())
    var state = _state.asStateFlow()

    fun onIntent(intent: ForecastDailyIntent) {
        logger.d("onEvent() called with: event = [$intent]")
        viewModelScope.launch {
//            when (event) {
//
//            }
        }
    }

    private suspend fun getDailyForecast() {
        getDailyForecastUseCase().collectLatest { result ->

        }
    }
}