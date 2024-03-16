package com.example.revirotestapp.ui.fragments.details

import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.revirotestapp.R
import com.example.revirotestapp.core.base.BaseFragment
import com.example.revirotestapp.databinding.FragmentDetailsBinding
import com.example.revirotestapp.ui.adapter.WeatherInfoPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment :
    BaseFragment<FragmentDetailsBinding, DetailsViewModel>(R.layout.fragment_details) {

    override val binding by viewBinding(FragmentDetailsBinding::bind)
    override val viewModel by viewModel<DetailsViewModel>()

    private val args by navArgs<DetailsFragmentArgs>()

    private val pagerAdapter: WeatherInfoPagerAdapter by lazy {
        WeatherInfoPagerAdapter(args.size,this).apply {

        }
    }

    override fun initialize() {
        binding.vpWeather.apply {
            adapter = pagerAdapter
        }
        binding.dotsIndicator.attachTo(binding.vpWeather)
        binding.vpWeather.post {
            binding.vpWeather.setCurrentItem(args.position, false)
        }
    }
}