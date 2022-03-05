package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.roomdemo.databinding.ActivityMainBinding
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}