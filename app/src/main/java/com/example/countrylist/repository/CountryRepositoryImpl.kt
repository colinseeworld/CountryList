package com.example.countrylist.repository

import com.example.countrylist.data.Results
import com.example.countrylist.data.api.ApiService
import com.example.countrylist.data.entity.CountryListResponse

class CountryRepositoryImpl(private val apiService: ApiService) : CountryListRepository {

    override suspend fun getCountries(): Results<List<CountryListResponse>> {
        return try {
            val response = apiService.getCountries()
            if (response.isSuccessful) {
                Results.Success(response.body() ?: emptyList())
            } else {
                Results.Error(Throwable("Failed to fetch data"))
            }
        } catch (e: Exception) {
            Results.Error(e)
        }
    }

}