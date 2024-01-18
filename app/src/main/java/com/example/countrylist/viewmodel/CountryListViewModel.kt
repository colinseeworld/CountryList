package com.example.countrylist.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrylist.data.Results
import com.example.countrylist.data.entity.CountryListResponse
import com.example.countrylist.repository.CountryListRepository
import com.example.countrylist.utility.CoreUtility
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val countryRepository: CountryListRepository
) : ViewModel() {

    private val _countryListFlow =
        MutableStateFlow<Results<List<CountryListResponse>>>(Results.Loading())
    val countryListFlow: StateFlow<Results<List<CountryListResponse>>> = _countryListFlow

    fun fetchCountryData() {
        viewModelScope.launch {
                try {
                    val result = countryRepository.getCountries()
                    if (result.data != null) {
                        _countryListFlow.value = result
                    } else {
                        handleFetchDataError("Failed to fetch data")
                    }
                } catch (e: Exception) {
                    handleFetchDataError(e.message ?: "An error occurred")
                }
            }
        }


    private fun handleFetchDataError(errorMessage: String) {
        _countryListFlow.value = Results.Error(Throwable(errorMessage))
    }
}