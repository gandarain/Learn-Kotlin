package com.example.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ItemsRowBinding

/** Todo
 * We have the @param [items] to represent the list that populates the adapter
 * The @param [updateListener] to listen to the edit icon an get the positions id
 * The @param [deleteListener] to listen to the delete icon and get the positions id
 **/
class EmployeeAdapter(
    private val employeeList: ArrayList<EmployeeEntity>,
    private val updateListener: (id: Int) -> Unit,
    private val deleteListener: (id: Int) -> Unit
): RecyclerView.Adapter<EmployeeAdapter.MainViewHolder>() {

    /**
     * A MainViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    inner class MainViewHolder(private val itemBinding: ItemsRowBinding) : RecyclerView.ViewHolder(itemBinding.root){
        val linerLayoutMain = itemBinding.linearLayoutItem
        val textViewName = itemBinding.textViewName
        val textViewEmail = itemBinding.textViewEmail
        val imageViewEdit = itemBinding.imageViewEdit
        val imageViewDelete = itemBinding.imageViewDelete
    }

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemsRowBinding.inflate(
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
        val employee = employeeList[position]
        val context = holder.itemView.context

        holder.textViewName.text = employee.name
        holder.textViewEmail.text = employee.email

        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0) {
            holder.linerLayoutMain.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.lightGray
                )
            )
        } else {
            holder.linerLayoutMain.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
        }

        // Todo set onclick listItem on the icon and invoke update and delete listeners
        holder.imageViewEdit.setOnClickListener {
             updateListener.invoke(employee.id)
        }

        holder.imageViewDelete.setOnClickListener {
             deleteListener.invoke(employee.id)
        }
        // end
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return employeeList.size
    }
}