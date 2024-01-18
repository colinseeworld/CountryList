package com.example.countrylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countrylist.repository.CountryListRepository


class CountryListViewModelFactory(private val countryRepository: CountryListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CountryListViewModel(countryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
