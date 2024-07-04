package com.example.tsfbankapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tsfbankapp.model.Customer


@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Query("SELECT * FROM customer_table")
    fun getAllCustomer(): LiveData<List<Customer>>

    @Query("SELECT * FROM customer_table WHERE customer_id IN (:idArray)")
    suspend fun getCustomerExcept(idArray: List<Long>): List<Customer>

    @Query("SELECT * FROM customer_table WHERE customer_id = (:customerId) ")
    suspend fun getCustomerById(customerId: Long): Customer

}