package com.example.revirotestapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.model.CityModel
import com.example.domain.model.WeatherResponseModel
import com.example.revirotestapp.core.extensions.loadImage
import com.example.revirotestapp.core.extensions.toFormattedTime
import com.example.revirotestapp.core.extensions.toImageUrl
import com.example.revirotestapp.core.utils.Constants.DATE_TIME_FORMAT_PATTERN
import com.example.revirotestapp.core.utils.Constants.TIME_FORMAT_PATTERN_12H
import com.example.revirotestapp.databinding.ItemWeatherBinding

class WeatherListAdapter(
    private val onItemClick: (position: Int) -> Unit,
    private val onDeleteClick: (city: CityModel) -> Unit,
) :
    ListAdapter<WeatherResponseModel, WeatherListAdapter.WeatherViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeatherViewHolder(
        ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class WeatherViewHolder(private val binding: ItemWeatherBinding) :
        ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: WeatherResponseModel): Unit = with(binding) {
            tvTemp.text = "${model.list.first().main.temp.toInt()}°ᶜ"
            tvCity.text = model.city.name
            tvDate.text = model.list.first().date.toFormattedTime(
                DATE_TIME_FORMAT_PATTERN,
                TIME_FORMAT_PATTERN_12H
            )
            tvDescription.text = model.list.first().weather.first().description
            ivWeatherIc.loadImage(model.list.first().weather.first().icon.toImageUrl(4))
        }

        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }

            binding.btnDelete.setOnClickListener {
                getItem(adapterPosition)?.let {
                    onDeleteClick(it.city)
                }
            }
        }
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<WeatherResponseModel>() {
            override fun areItemsTheSame(
                oldItem: WeatherResponseModel,
                newItem: WeatherResponseModel
            ) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(
                oldItem: WeatherResponseModel,
                newItem: WeatherResponseModel
            ) =
                oldItem == newItem
        }
    }
}