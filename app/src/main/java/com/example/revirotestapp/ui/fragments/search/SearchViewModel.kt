package com.example.revirotestapp.ui.fragments.search

import androidx.lifecycle.viewModelScope
import com.example.domain.model.CityModel
import com.example.domain.use_case.InsertCityUseCase
import com.example.domain.use_case.SearchCityUseCase
import com.example.revirotestapp.core.base.BaseViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchCityUseCase: SearchCityUseCase,
    private val insertCityUseCase: InsertCityUseCase
) : BaseViewModel() {

    private val _state = mutableUiStateFlow<List<CityModel>>()
    val state = _state.asStateFlow()

    fun searchCities(city: String) {
        searchCityUseCase(city).gatherRequest(_state)
    }

    fun insertCity(cityModel: CityModel) {
        viewModelScope.launch {
            insertCityUseCase(cityModel).toString()
        }
    }
}