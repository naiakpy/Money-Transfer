package com.example.tsfbankapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tsfbankapp.database.CustomerDao

class CustomersViewModelProviderFactory(private val datasource: CustomerDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomersViewModel::class.java)) {
            return CustomersViewModel(datasource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}