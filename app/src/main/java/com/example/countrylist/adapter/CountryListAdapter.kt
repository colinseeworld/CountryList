package com.example.countrylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.data.Results
import com.example.countrylist.data.entity.CountryListResponse
import com.example.countrylist.databinding.CountryListRowBinding

class CountryListAdapter : RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    private var countryList: Results<List<CountryListResponse>> = Results.Loading()
    fun submitCountryList(newCountryList: Results<List<CountryListResponse>>) {
        countryList = newCountryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountryListRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        when (countryList) {
            is Results.Success -> {
                val country = countryList.data?.get(position)
                holder.bind(country)
            }

            is Results.Error -> {}
            is Results.Loading -> {}
        }
    }

    override fun getItemCount(): Int = countryList.data?.size ?: 0
    inner class CountryViewHolder(private val binding: CountryListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: CountryListResponse?) {
            with(binding) {
                tvName.text = country?.name
                tvRegion.text = country?.region
                tvCode.text = country?.code
                tvCapital.text = country?.capital
            }
        }
    }
}