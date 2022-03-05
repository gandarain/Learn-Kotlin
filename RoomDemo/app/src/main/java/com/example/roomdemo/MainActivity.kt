package com.example.roomdemo

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.databinding.DialogUpdateBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Todo: get the employeeDao variable through the application class
        val employeeDao = (application as EmployeeApp).db.employeeDao()

        binding?.buttonAdd?.setOnClickListener {
            // Todo pass in the employeeDao
            addRecord(employeeDao)
        }

        // Todo launch a coroutine block and fetch all employee
        lifecycleScope.launch {
            employeeDao.fetchAllEmployees().collect {
                val employeeList = ArrayList(it)
                setupListEmployee(employeeList, employeeDao)
            }
        }
    }

    // Todo create an employeeDao param to access the insert method
    // launch a coroutine block to call the method for inserting entry
    private fun addRecord(employeeDao: EmployeeDao){
        val name = binding?.editTextName?.text.toString()
        val email = binding?.editTextEmail?.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty()) {
            lifecycleScope.launch {
                employeeDao.insert(EmployeeEntity(name = name, email = email))
                Toast.makeText(this@MainActivity, "Record saved!", Toast.LENGTH_SHORT).show()
                binding?.editTextName?.text?.clear()
                binding?.editTextEmail?.text?.clear()
            }
        } else {
            Toast.makeText(this@MainActivity, "Name and email can't be empty.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Todo: create an employee param to pass into updateRecordDialog and updateRecordDialog
     * method
     * Function is used show the list of inserted data.
     */
    private fun setupListEmployee(employeeList: ArrayList<EmployeeEntity>, employeeDao: EmployeeDao) {
        if (employeeList.isNotEmpty()) {
            // Adapter class is initialized and list is passed in the param.
            val employeeAdapter = EmployeeAdapter(
                employeeList,
                {
                    updateId ->
                    updateRecordDialog(updateId, employeeDao)
                },
                {
                    deletedId ->
                    deleteRecordDialog(deletedId, employeeDao)
                }
            )
            // Set the LayoutManager that this RecyclerView will use.
            binding?.recyclerViewEmployeeList?.layoutManager = LinearLayoutManager(this)
            // adapter instance is set to the recyclerview to inflate the items.
            binding?.recyclerViewEmployeeList?.adapter = employeeAdapter
            binding?.textViewNoRecords?.visibility = View.INVISIBLE
            binding?.recyclerViewEmployeeList?.visibility = View.VISIBLE
        } else {
            binding?.textViewNoRecords?.visibility = View.VISIBLE
            binding?.recyclerViewEmployeeList?.visibility = View.GONE
        }
    }

    /**Todo:  create an id param for identifying the row to be updated
     * Create an employeeDao param for accessing method from the dao
     * We also launch a coroutine block to fetch the selected employee and update it
     */
    private fun updateRecordDialog(id: Int, employeeDao: EmployeeDao) {
        val dialog = Dialog(this, R.style.Theme_Dialog)
        dialog.setCancelable(false)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        val binding = DialogUpdateBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        lifecycleScope.launch {
            employeeDao.fetchAllEmployeeById(id).collect {
                if (it != null) {
                    binding.editTextUpdateName.setText(it.name)
                    binding.editTextUpdateEmail.setText(it.email)
                }
            }
        }

        binding.textViewUpdate.setOnClickListener {
            val name = binding.editTextUpdateName.text.toString()
            val email = binding.editTextUpdateEmail.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                lifecycleScope.launch {
                    employeeDao.update(EmployeeEntity(id, name, email))
                    Toast.makeText(this@MainActivity, "Record updated!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(this@MainActivity, "Name and email can't be empty.", Toast.LENGTH_SHORT).show()
            }
        }


        binding.textViewCancel.setOnClickListener {
            dialog.dismiss()
        }

        // Start the dialog and display it on screen.
        dialog.show()
    }

    /** Todo
     * Method is used to show the Alert Dialog and delete the selected employee.
     * We add an id to get the selected position and an employeeDao param to get the
     * methods from the dao interface then launch a coroutine block to call the methods
     */
    private fun deleteRecordDialog(id: Int, employeeDao: EmployeeDao) {
        val builder = AlertDialog.Builder(this)

        // set title for alert dialog
        builder.setTitle("Delete Record")

        builder.setIcon(android.R.drawable.ic_dialog_alert)

        // performing positive action
        builder.setPositiveButton("Yes"){ dialogInterface, _ ->
            lifecycleScope.launch {
                employeeDao.delete(EmployeeEntity(id))
                Toast.makeText(this@MainActivity, "Record deleted!", Toast.LENGTH_SHORT).show()
                // Dialog will be dismissed
                dialogInterface.dismiss()
            }
        }

        // performing negative action
        builder.setNegativeButton("No"){ dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show() // show the dialog to UI
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}