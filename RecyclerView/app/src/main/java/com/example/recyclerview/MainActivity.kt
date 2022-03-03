package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val taskAdapter = TaskAdapter(TaskList.taskList)
        binding?.recyclerViewTask?.adapter = taskAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}