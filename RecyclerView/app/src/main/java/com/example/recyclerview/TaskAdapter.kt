package com.example.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.RecyclerViewItemBinding

class TaskAdapter(val taskList: List<Task>): RecyclerView.Adapter<TaskAdapter.MainViewHolder>() {
    inner class MainViewHolder(val itemBinding: RecyclerViewItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root){
            fun bindItem(task: Task) {
                itemBinding.textViewTaskTitle.text = task.title
                itemBinding.textViewTaskTime.text = task.timeStamp
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.MainViewHolder {
        return MainViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskAdapter.MainViewHolder, position: Int) {
        val task = taskList[position]
        holder.bindItem(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}