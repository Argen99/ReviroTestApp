package com.example.revirotestapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.revirotestapp.ui.fragments.details.WeatherInfoFragment

class WeatherInfoPagerAdapter(private val size: Int,fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment {
        return WeatherInfoFragment.newInstance(position)
    }
}