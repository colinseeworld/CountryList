package com.example.countrylist.data.api

import com.example.countrylist.data.entity.CountryListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("countries.json")
    suspend fun getCountries(): Response<List<CountryListResponse>>
}