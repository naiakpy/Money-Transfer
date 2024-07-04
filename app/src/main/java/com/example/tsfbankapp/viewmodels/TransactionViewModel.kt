package com.example.tsfbankapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsfbankapp.database.CustomerDao
import com.example.tsfbankapp.model.Customer
import kotlinx.coroutines.launch

class TransactionViewModel(private var databaseSource: CustomerDao) : ViewModel() {

    private var customerListId = listOf<Long>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    private var requiredIdList = ArrayList<Long>()

    private var _updatedCustomerList = MutableLiveData<List<Customer>>()
    var updatedCustomerList: LiveData<List<Customer>> = _updatedCustomerList

    fun showCustomerList(){
        viewModelScope.launch {
            _updatedCustomerList.value = databaseSource.getCustomerExcept(requiredIdList)
        }
    }

    fun updateCustomerList(customer: Customer) {

        for (id in customerListId) {
            if (id != customer.id) {
                requiredIdList.add(id)
            }
        }
    }
}