package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupToolbar()

        // setup exercise dao
        val exerciseHistoryDao = (application as ExerciseHistoryApp).db.exerciseHistoryDao()

        loadExerciseData(exerciseHistoryDao)
    }

    /**
     * Setup the toolbar
     */
    private fun setupToolbar() {
        // set the toolbar
        setSupportActionBar(binding?.toolBarHistory)

        // setup the back button
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "EXERCISE HISTORY"
        }
        // on press back
        binding?.toolBarHistory?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadExerciseData(exerciseHistoryDao: ExerciseHistoryDao) {
        lifecycleScope.launch {
            exerciseHistoryDao.fetchAllExerciseHistory().collect {
                val exerciseList = ArrayList(it)
                if (exerciseList.isEmpty()) {
                    emptyHistory()
                } else {
                    showExerciseHistory(exerciseList)
                }
            }
        }
    }

    private fun emptyHistory() {
        binding?.recyclerViewExerciseHistoryList?.visibility = View.INVISIBLE
        binding?.textViewEmptyHistory?.visibility = View.VISIBLE
    }

    private fun showExerciseHistory(exerciseHistory: ArrayList<ExerciseHistoryEntity>) {
        binding?.textViewEmptyHistory?.visibility = View.INVISIBLE
        binding?.recyclerViewExerciseHistoryList?.visibility = View.VISIBLE

        // Creates a vertical Layout Manager
        binding?.recyclerViewExerciseHistoryList?.layoutManager = LinearLayoutManager(this@HistoryActivity)

        // History adapter is initialized and the list is passed in the param.
        val dates = ArrayList<String>()
        for (date in exerciseHistory){
            dates.add(date.date)
        }
        val historyAdapter = ExerciseHistoryAdapter(dates)

        // Access the RecyclerView Adapter and load the data into it
        binding?.recyclerViewExerciseHistoryList?.adapter = historyAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}