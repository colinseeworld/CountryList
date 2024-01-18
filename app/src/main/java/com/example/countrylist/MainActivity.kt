package com.example.countrylist

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countrylist.adapter.CountryListAdapter
import com.example.countrylist.data.api.ApiService
import com.example.countrylist.data.api.RetrofitInstance
import com.example.countrylist.databinding.ActivityMainBinding
import com.example.countrylist.repository.CountryRepositoryImpl
import com.example.countrylist.viewmodel.CountryListViewModel
import com.example.countrylist.viewmodel.CountryListViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countryListAdapter: CountryListAdapter

    private val apiService =
        RetrofitInstance.createRetrofitInstance().create(ApiService::class.java)
    private val countryRepository = CountryRepositoryImpl(apiService)
    private val viewModel: CountryListViewModel by viewModels {
        CountryListViewModelFactory(
            countryRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initViewModel()

        viewModel.fetchCountryData()
    }

    private fun initRecyclerView() {
        binding.countryListRv.layoutManager = LinearLayoutManager(this)
        countryListAdapter = CountryListAdapter()
        binding.countryListRv.adapter = countryListAdapter
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countryListFlow.collect { countryList ->
                    countryListAdapter.submitCountryList(countryList)
                    countryListAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main)
        } else {
            setContentView(R.layout.activity_main)
        }
    }
}