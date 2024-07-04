package com.example.tsfbankapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tsfbankapp.adapters.CustomersAdapter
import com.example.tsfbankapp.adapters.ItemCustomerClickListener
import com.example.tsfbankapp.database.CustomerDatabase
import com.example.tsfbankapp.databinding.FragmentTransactionsBinding
import com.example.tsfbankapp.viewmodels.TransactionViewModel
import com.example.tsfbankapp.viewmodels.TransactionViewModelProviderFactory

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var viewModel: TransactionViewModel

    private val args by navArgs<TransactionsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTransactionsBinding.inflate(inflater)

        val amount = args.transferAmount
        val senderCustomer = args.customer

        val application = requireNotNull(this.activity).application
        val dataSource = CustomerDatabase.getInstance(application).customerDao
        val viewModelFactory = TransactionViewModelProviderFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[TransactionViewModel::class.java]

        val adapter = CustomersAdapter(ItemCustomerClickListener { receiverCustomer ->
            val action =
                TransactionsFragmentDirections.actionTransactionsFragmentToSuccessfulTransactionFragment(
                    senderCustomer,
                    receiverCustomer,
                    amount
                )
            findNavController().navigate(action)
        })

        viewModel.updateCustomerList(senderCustomer)

        viewModel.showCustomerList()

        binding.transactionRecyclerView.adapter = adapter

        viewModel.updatedCustomerList.observe(viewLifecycleOwner) { customerList ->
            adapter.submitList(customerList)
        }



        return binding.root
    }
}