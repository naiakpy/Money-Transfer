package com.example.tsfbankapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsfbankapp.database.CustomerDao
import com.example.tsfbankapp.model.Customer
import kotlinx.coroutines.launch

class CustomersViewModel(private val datasource: CustomerDao): ViewModel() {

    lateinit var customerList: LiveData<List<Customer>>

    init {
        getCustomerList()
    }

    private fun getCustomerList() {
        viewModelScope.launch {
            customerList = datasource.getAllCustomer()
        }
    }

}