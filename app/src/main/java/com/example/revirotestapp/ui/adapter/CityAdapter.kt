package com.example.revirotestapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.model.CityModel
import com.example.revirotestapp.databinding.ItemCityBinding

class CityAdapter(
    private val onAddItemClick:(model: CityModel) -> Unit
): ListAdapter<CityModel, CityAdapter.CityViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CityViewHolder(
        ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class CityViewHolder(private val binding: ItemCityBinding): ViewHolder(binding.root) {

        fun bind(model: CityModel): Unit = with(binding) {
            tvCity.text = model.name
        }

        init {
            binding.btnAddCity.setOnClickListener {
                onAddItemClick(getItem(adapterPosition))
            }
        }
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<CityModel>() {
            override fun areItemsTheSame(oldItem: CityModel, newItem: CityModel) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(oldItem: CityModel, newItem: CityModel) =
                oldItem == newItem
        }
    }
}