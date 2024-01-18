package com.example.countrylist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countrylist.adapter.CountryListAdapter
import com.example.countrylist.data.Results
import com.example.countrylist.data.api.ApiService
import com.example.countrylist.data.api.RetrofitInstance
import com.example.countrylist.data.entity.CountryListResponse
import com.example.countrylist.databinding.ActivityMainBinding
import com.example.countrylist.repository.CountryRepositoryImpl
import com.example.countrylist.utility.CoreUtility
import com.example.countrylist.viewmodel.CountryListViewModel
import com.example.countrylist.viewmodel.CountryListViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countryListAdapter: CountryListAdapter
    private var countryList: Results<List<CountryListResponse>> = Results.Success(emptyList())

    private val apiService =
        RetrofitInstance.createRetrofitInstance().create(ApiService::class.java)
    private val countryRepository = CountryRepositoryImpl(apiService)

    private val viewModelFactory = CountryListViewModelFactory(countryRepository)

    private lateinit var countryListViewModel: CountryListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        countryListViewModel = ViewModelProvider(this, viewModelFactory)[CountryListViewModel::class.java]

        if (savedInstanceState == null && CoreUtility.isInternetConnected(this)) {
            countryListViewModel.fetchCountryData()
        } else {
            Snackbar.make(
                binding.root,
                "No internet connection",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun initRecyclerView() {
        binding.countryListRv.layoutManager = LinearLayoutManager(this)
        countryListAdapter = CountryListAdapter(countryList)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryListViewModel.countryListFlow.collect { newCountryList ->
                    countryList = newCountryList
                    binding.countryListRv.adapter = countryListAdapter
                    countryListAdapter.setCountryList(countryList)
                }
            }
        }
    }

}