package com.example.roomdemo


import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Todo 1: create the dao interface
@Dao
interface EmployeeDao {
    // Todo 2: create a suspend insert function for saving an entry
    @Insert
    suspend fun insert(employeeEntity: EmployeeEntity)

    // Todo 3: create a suspend update function for updating an existing entry
    @Update
    suspend fun update(employeeEntity: EmployeeEntity)

    // Todo 4: create a suspend delete function for deleting an existing entry
    @Delete
    suspend fun delete(employeeEntity: EmployeeEntity)

    // Todo 5: create a function to read all employee, this returns a Flow
    @Query("SELECT * FROM `employee-table`")
    // flow is used to hold a value that can change at runtime
    fun fetchAllEmployees(): Flow<List<EmployeeEntity>>

    // Todo 6: create a function to read one employee, this returns a Flow
    @Query("SELECT * FROM `employee-table` WHERE id=:id")
    fun fetchAllEmployeeById(id: Int): Flow<EmployeeEntity>
}