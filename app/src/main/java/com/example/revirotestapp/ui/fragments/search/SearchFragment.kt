package com.example.revirotestapp.ui.fragments.search

import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.model.CityModel
import com.example.revirotestapp.R
import com.example.revirotestapp.core.base.BaseFragment
import com.example.revirotestapp.databinding.FragmentSearchBinding
import com.example.revirotestapp.ui.adapter.CityAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment :
    BaseFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {

    override val binding by viewBinding(FragmentSearchBinding::bind)
    override val viewModel by viewModel<SearchViewModel>()

    private val cityAdapter: CityAdapter by lazy {
        CityAdapter(::onAddItemClick)
    }

    override fun initialize() {
        binding.rvCities.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = cityAdapter
        }
    }

    override fun setupListeners() {
        binding.searchView.addTextChangedListener {
            viewModel.searchCities(it.toString())
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onAddItemClick(model: CityModel) {
        viewModel.insertCity(model)
    }

    override fun launchObservers() {
        viewModel.state.spectateUiState(
            success = { cityAdapter.submitList(it) }
        )
    }
}