package com.example.revirotestapp.ui.fragments.details

import android.annotation.SuppressLint
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.model.WeatherResponseModel
import com.example.revirotestapp.R
import com.example.revirotestapp.core.base.BaseFragment
import com.example.revirotestapp.core.extensions.loadImage
import com.example.revirotestapp.core.extensions.toFormattedTime
import com.example.revirotestapp.core.extensions.toImageUrl
import com.example.revirotestapp.core.utils.Constants.DATE_FORMAT_PATTERN_LONG
import com.example.revirotestapp.core.utils.Constants.DATE_TIME_FORMAT_PATTERN
import com.example.revirotestapp.core.utils.Constants.TIME_FORMAT_PATTERN
import com.example.revirotestapp.databinding.FragmentWeatherInfoBinding
import com.example.revirotestapp.ui.fragments.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class WeatherInfoFragment :
    BaseFragment<FragmentWeatherInfoBinding, MainViewModel>(R.layout.fragment_weather_info) {

    override val binding by viewBinding(FragmentWeatherInfoBinding::bind)
    override val viewModel by activityViewModel<MainViewModel>()

    private val position: Int by lazy {
        arguments?.getInt(ARG_KEY) ?: 0
    }
    private val images = listOf(R.drawable.screen_1,R.drawable.screen_2,R.drawable.screen_3)

    override fun initialize() {
        viewModel.state.spectateUiState(
            success = { data ->
                setUIData(data[position])
            }
        )
        setBackgroundImagery(position, images)
    }

    @SuppressLint("SetTextI18n")
    private fun setUIData(data: WeatherResponseModel): Unit = with(binding) {
        tvCity.text = data.city.name
        tvTemp.text = "${data.list.first().main.temp.toInt()}°ᶜ"
        tvDate.text = data.list.first().date.toFormattedTime(
            DATE_TIME_FORMAT_PATTERN,
            DATE_FORMAT_PATTERN_LONG
        )
        tvUvIndex.text = "0"
        tvWind.text = "${data.list.first().wind.speed.toInt()} m/s"
        tvHumidity.text = "${data.list.first().main.humidity}%"

        incTemp1.apply {
            this.tvTime.text = "Now"
            this.tvTemp.text = "${data.list.first().main.temp.toInt()}°ᶜ"
            this.ivIcon.loadImage(data.list.first().weather.first().icon.toImageUrl())
        }

        incTemp2.apply {
            this.tvTime.text =
                data.list[1].date.toFormattedTime(DATE_TIME_FORMAT_PATTERN, TIME_FORMAT_PATTERN)
            this.tvTemp.text = "${data.list[1].main.temp.toInt()}°ᶜ"
            this.ivIcon.loadImage(data.list[1].weather.first().icon.toImageUrl())
        }

        incTemp3.apply {
            this.tvTime.text =
                data.list[2].date.toFormattedTime(DATE_TIME_FORMAT_PATTERN, TIME_FORMAT_PATTERN)
            this.tvTemp.text = "${data.list[2].main.temp.toInt()}°ᶜ"
            this.ivIcon.loadImage(data.list[2].weather.first().icon.toImageUrl())
        }

        incTemp4.apply {
            this.tvTime.text =
                data.list[3].date.toFormattedTime(DATE_TIME_FORMAT_PATTERN, TIME_FORMAT_PATTERN)
            this.tvTemp.text = "${data.list[3].main.temp.toInt()}°ᶜ"
            this.ivIcon.loadImage(data.list[3].weather.first().icon.toImageUrl())
        }

        incTemp5.apply {
            this.tvTime.text =
                data.list[4].date.toFormattedTime(DATE_TIME_FORMAT_PATTERN, TIME_FORMAT_PATTERN)
            this.tvTemp.text = "${data.list[4].main.temp.toInt()}°ᶜ"
            this.ivIcon.loadImage(data.list[4].weather.first().icon.toImageUrl())
        }
    }

    private fun setBackgroundImagery(pageIndex: Int, imagesList: List<Int>) {
        val numberOfImages = imagesList.size
        val imageIndex = pageIndex % numberOfImages
        val selectedImage = imagesList[imageIndex]

        binding.root.setBackgroundResource(selectedImage)
    }

    companion object {
        private const val ARG_KEY = "position"
        fun newInstance(position: Int): WeatherInfoFragment {
            val fragment = WeatherInfoFragment()
            val args = Bundle()
            args.putInt(ARG_KEY, position)
            fragment.arguments = args
            return fragment
        }
    }
}