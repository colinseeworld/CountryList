package com.example.countrylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrylist.data.Results
import com.example.countrylist.data.entity.CountryListResponse
import com.example.countrylist.repository.CountryListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryListViewModel(private val countryRepository: CountryListRepository) : ViewModel() {

    private val _countryListFlow =
        MutableStateFlow<Results<List<CountryListResponse>>>(Results.Loading())
    val countryListFlow: StateFlow<Results<List<CountryListResponse>>> = _countryListFlow

    fun fetchCountryData() {
        viewModelScope.launch {
            try {
                val result = countryRepository.getCountries()
                _countryListFlow.value = result
            } catch (e: Exception) {
                _countryListFlow.value = Results.Error(e)
            }
        }
    }

}