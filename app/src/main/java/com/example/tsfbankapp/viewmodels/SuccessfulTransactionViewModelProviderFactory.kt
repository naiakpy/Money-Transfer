package com.example.tsfbankapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tsfbankapp.database.CustomerDao
import com.example.tsfbankapp.database.TransactionRecordDao

class SuccessfulTransactionViewModelProviderFactory (

    private val customerDatasource: CustomerDao,
    private val transactionRecordDatasource: TransactionRecordDao

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuccessfulTransactionViewModel::class.java)) {
            return SuccessfulTransactionViewModel(
                customerDatasource,
                transactionRecordDatasource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}