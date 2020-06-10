package dev.beriashvili.assignments.mvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.beriashvili.assignments.mvvm.databinding.ApartmentRecyclerviewLayoutBinding
import dev.beriashvili.assignments.mvvm.models.Apartment

class ApartmentRecyclerViewAdapter(private val apartments: List<Apartment>) :
    RecyclerView.Adapter<ApartmentRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ApartmentRecyclerviewLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = apartments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }

    inner class ViewHolder(private val binding: ApartmentRecyclerviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.apartment = apartments[adapterPosition]
        }
    }
}