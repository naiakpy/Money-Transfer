package com.example.tsfbankapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tsfbankapp.database.TransactionRecordDao

class TransactionRecordViewModelProviderFactory(private val transactionRecordDatasource: TransactionRecordDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionRecordViewModel::class.java)) {
            return TransactionRecordViewModel(
                transactionRecordDatasource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}