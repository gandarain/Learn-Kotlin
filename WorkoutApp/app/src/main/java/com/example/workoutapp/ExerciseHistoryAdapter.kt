package com.example.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ActivityHistoryItemBinding

/** Todo
 * We have the @param [items] to represent the list that populates the adapter
 **/
class ExerciseHistoryAdapter(
    private val exerciseHistoryList: ArrayList<String>
): RecyclerView.Adapter<ExerciseHistoryAdapter.MainViewHolder>() {
    /**
     * A MainViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    inner class MainViewHolder(private val itemBinding: ActivityHistoryItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        var textViewNumber = itemBinding.textViewNumber
        var textViewDate = itemBinding.textViewDate
        var historyListItem = itemBinding.historyListItem
    }

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ActivityHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val date: String = exerciseHistoryList.get(position)
        val context = holder.itemView.context

        holder.textViewNumber.text = "${(position + 1)}."
        holder.textViewDate.text = date

        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0) {
            holder.historyListItem.setBackgroundColor(
                ContextCompat.getColor(context, R.color.softGrey)
            )
        } else {
            holder.historyListItem.setBackgroundColor(
                ContextCompat.getColor(context, R.color.white)
            )
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return exerciseHistoryList.size
    }
}