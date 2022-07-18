package com.example.countrieschallange.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countrieschallange.databinding.CountryItemBinding
import com.example.countrieschallange.model.Country

class CountriesAdapter(
    private val countriesList: MutableList<Country> = mutableListOf(),
): RecyclerView.Adapter<CountriesAdapter.CountryItemViewHolder>() {

    inner class CountryItemViewHolder(
        private val binding: CountryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(countryData: Country) {
            binding.tvCountryName.text = "${countryData.name}, ${countryData.region}"
            binding.tvCapital.text = countryData.capital
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryItemViewHolder(
            CountryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        holder.onBind(countriesList[position])
    }

    override fun getItemCount() = countriesList.size
}