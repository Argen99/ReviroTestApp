package com.example.revirotestapp.ui.fragments

import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.model.CityModel
import com.example.revirotestapp.R
import com.example.revirotestapp.core.base.BaseFragment
import com.example.revirotestapp.databinding.FragmentMainBinding
import com.example.revirotestapp.ui.adapter.WeatherListAdapter
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(R.layout.fragment_main) {

    override val binding by viewBinding(FragmentMainBinding::bind)
    override val viewModel by activityViewModel<MainViewModel>()

    private val weatherAdapter: WeatherListAdapter by lazy {
        WeatherListAdapter(::onItemClick, ::onDeleteItemClick)
    }

    override fun initialize() {
        binding.rvWeather.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = weatherAdapter
        }
    }

    override fun setupListeners() {
        binding.btnAddCity.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }

    override fun launchObservers() {
        viewModel.state.spectateUiState(
            loading = {
                binding.progressBar.isVisible = true
            },
            success = {
                binding.progressBar.isGone = true
                weatherAdapter.submitList(it)
            },
            error = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                binding.progressBar.isGone = true
            }
        )
    }

    private fun onItemClick(position: Int) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailsFragment(
                position, weatherAdapter.itemCount)
        )
    }

    private fun onDeleteItemClick(city: CityModel) {
        viewModel.deleteCity(city)
    }
}