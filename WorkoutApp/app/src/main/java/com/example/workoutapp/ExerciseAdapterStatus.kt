package com.example.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemExerciseStatusBinding

class ExerciseAdapterStatus(val items: ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseAdapterStatus.ViewHolder>()  {
    inner class ViewHolder(binding: ItemExerciseStatusBinding): RecyclerView.ViewHolder(binding.root) {
        val textViewItemStatus = binding.textViewItemStatus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.textViewItemStatus.text = model.getId().toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}