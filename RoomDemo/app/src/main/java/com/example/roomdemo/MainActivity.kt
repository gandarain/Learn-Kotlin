package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
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
            val employeeAdapter = EmployeeAdapter(employeeList)
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}