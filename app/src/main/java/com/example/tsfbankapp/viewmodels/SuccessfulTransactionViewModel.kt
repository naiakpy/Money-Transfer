package com.example.tsfbankapp.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsfbankapp.database.CustomerDao
import com.example.tsfbankapp.database.TransactionRecordDao
import com.example.tsfbankapp.model.Customer
import com.example.tsfbankapp.model.TransactionRecord
import kotlinx.coroutines.launch

class SuccessfulTransactionViewModel (
    private val datasource: CustomerDao,
    private val transactionRecordDatasource: TransactionRecordDao
) : ViewModel() {

    private var _senderCustomer = MutableLiveData<Customer>()
    var senderCustomer: LiveData<Customer> = _senderCustomer

    private var _receiverCustomer = MutableLiveData<Customer>()
    var receiverCustomer: LiveData<Customer> = _receiverCustomer


    private var _navigateToCustomersScreen = MutableLiveData<Boolean>()
    var navigateToCustomersScreen: LiveData<Boolean> = _navigateToCustomersScreen


    fun initiateTransaction(
        context: Context,
        senderCustomer: Customer,
        receiverCustomer: Customer,
        transferAmount: Int
    ) {
        if (senderCustomer.accountBalance > transferAmount) {

            val remainingAmount = senderCustomer.accountBalance - transferAmount
            val updatedAmount = senderCustomer.accountBalance + transferAmount

            val senderCustomerUpdate = Customer(
                senderCustomer.id,
                senderCustomer.customerName,
                senderCustomer.customerEmail,
                senderCustomer.customerMobileNumber,
                senderCustomer.customerAccountNumber,
                senderCustomer.swiftCode,
                remainingAmount,
                senderCustomer.bankName
            )

            val receiverCustomerUpdate = Customer(
                receiverCustomer.id,
                receiverCustomer.customerName,
                receiverCustomer.customerEmail,
                receiverCustomer.customerMobileNumber,
                receiverCustomer.customerAccountNumber,
                receiverCustomer.swiftCode,
                updatedAmount,
                receiverCustomer.bankName,
            )

            viewModelScope.launch {
                datasource.updateCustomer(senderCustomerUpdate)
            }

            viewModelScope.launch {
                datasource.updateCustomer(receiverCustomerUpdate)
            }

            val transactionRecord = TransactionRecord(
                0,
                senderCustomerUpdate.customerName,
                receiverCustomerUpdate.customerName,
                senderCustomerUpdate.customerAccountNumber,
                receiverCustomerUpdate.customerAccountNumber,
                transferAmount,
                true
            )

            updateSuccessfulTransactionRecord(transactionRecord)

            Toast.makeText(context, "Transaction Successful!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                context,
                "Transaction Failed! You have not sufficient balance.",
                Toast.LENGTH_LONG
            ).show()

            val transactionRecord = TransactionRecord(
                0,
                senderCustomer.customerName,
                receiverCustomer.customerName,
                senderCustomer.customerAccountNumber,
                receiverCustomer.customerAccountNumber,
                transferAmount,
                false
            )

            updateFailureTransactionRecord(transactionRecord)
            _navigateToCustomersScreen.value = true
        }
    }

    private fun updateSuccessfulTransactionRecord(
        transactionRecord: TransactionRecord
    ) {
        viewModelScope.launch {
            transactionRecordDatasource.insertTransaction(transactionRecord)
        }
    }

    private fun updateFailureTransactionRecord(
        transactionRecord: TransactionRecord
    ) {

        viewModelScope.launch {
            transactionRecordDatasource.insertTransaction(transactionRecord)
        }
    }

    fun updatedSenderCustomer(senderCustomer: Customer) {
        viewModelScope.launch {
            _senderCustomer.value = datasource.getCustomerById(senderCustomer.id)
        }
    }

    fun receiverSenderCustomer(receiverCustomer: Customer) {
        viewModelScope.launch {
            _receiverCustomer.value = datasource.getCustomerById(receiverCustomer.id)
        }
    }
}