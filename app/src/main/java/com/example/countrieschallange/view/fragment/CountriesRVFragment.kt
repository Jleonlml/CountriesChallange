package com.example.countrieschallange.view.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countrieschallange.cons.ErrorHandler
import com.example.countrieschallange.cons.UiState
import com.example.countrieschallange.databinding.CountryRecyclerViewBinding
import com.example.countrieschallange.model.Country
import com.example.countrieschallange.view.adapters.CountriesAdapter
import com.example.countrieschallange.view_model.CountryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountriesRVFragment: Fragment() {

    private val binding by lazy {
        CountryRecyclerViewBinding.inflate(layoutInflater)
    }

    private val countriesViewModel by viewModel<CountryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvCountries.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = countriesViewModel.countryAdapter
        }

        startLoading()
        configureObserver()
        return binding?.root
    }

    private fun configureObserver() {
        countriesViewModel.countryLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    binding?.spinner?.visibility = View.INVISIBLE
                    countriesViewModel.countryAdapter.setNewCountries(state.data as MutableList<Country>)
                }
                is UiState.Error -> {
                    ErrorHandler.displayError(context = requireContext(), retry = ::startLoading, message = state.error.message.toString())
                    binding?.spinner?.visibility = View.INVISIBLE
                }
                is UiState.Loading -> {
                    binding?.spinner?.visibility = View.VISIBLE
                    countriesViewModel.getCountries()
                }
            }
        }
    }

    private fun startLoading() {
        countriesViewModel.setLoadingState()
    }
}