package com.example.countrylist.repository

import com.example.countrylist.data.Results
import com.example.countrylist.data.entity.CountryListResponse

interface CountryListRepository {

    suspend fun getCountries(): Results<List<CountryListResponse>>

}