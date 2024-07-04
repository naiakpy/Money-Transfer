package com.example.tsfbankapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tsfbankapp.database.CustomerDao

class TransactionViewModelProviderFactory(private var databaseSource: CustomerDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(databaseSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}