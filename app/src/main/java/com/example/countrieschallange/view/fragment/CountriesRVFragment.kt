package com.example.countrieschallange.view.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.countrieschallange.cons.UiState
import com.example.countrieschallange.databinding.CountryRecyclerViewBinding
import com.example.countrieschallange.model.Country
import com.example.countrieschallange.view.adapters.CountriesAdapter
import com.example.countrieschallange.view_model.CountryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountriesRVFragment: Fragment() {

    private var _binding: CountryRecyclerViewBinding? = null
    private val binding: CountryRecyclerViewBinding? get() = _binding
    private val countriesViewModel by viewModel<CountryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CountryRecyclerViewBinding.inflate(layoutInflater)
        countriesViewModel.setLoadingState()
        configureObserver()
        return binding?.root
    }

    private fun configureObserver() {
        countriesViewModel.countryLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    countriesViewModel.countriesList = state.data as MutableList<Country>
                    countriesViewModel.countryItemAdapter = CountriesAdapter(
                        countriesList = countriesViewModel.countriesList
                    )
                    binding?.rvMovies?.adapter = countriesViewModel.countryItemAdapter
                    binding?.spinner?.visibility = View.INVISIBLE
                    binding?.tvErrorMsg?.visibility = View.INVISIBLE
                }
                is UiState.Error -> {
                    when (state.code) {
                        in 500..599 -> binding?.tvErrorMsg?.text = "servers are down :c"
                        in 400..499 -> binding?.tvErrorMsg?.text = "${state.message} \ncontact support team"
                    }
                    binding?.tvErrorMsg?.visibility = View.VISIBLE
                    binding?.spinner?.visibility = View.INVISIBLE
                }
                is UiState.Loading -> {
                    binding?.tvErrorMsg?.visibility = View.INVISIBLE
                    binding?.spinner?.visibility = View.VISIBLE
                    countriesViewModel.getCountries()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}