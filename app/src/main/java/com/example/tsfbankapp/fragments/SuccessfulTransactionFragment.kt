package com.example.tsfbankapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tsfbankapp.R
import com.example.tsfbankapp.database.CustomerDatabase
import com.example.tsfbankapp.databinding.FragmentSuccessfulTransactionBinding
import com.example.tsfbankapp.viewmodels.SuccessfulTransactionViewModel
import com.example.tsfbankapp.viewmodels.SuccessfulTransactionViewModelProviderFactory


class SuccessfulTransactionFragment : Fragment() {

    private lateinit var binding: FragmentSuccessfulTransactionBinding
    private lateinit var viewModel: SuccessfulTransactionViewModel

    private val args by navArgs<SuccessfulTransactionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSuccessfulTransactionBinding.inflate(inflater)

        val transferAmount = args.transferAmount
        val senderCustomer = args.senderCustomer
        val receiverCustomer = args.receiverCustomer

        val application = requireNotNull(this.activity).application
        val databaseInstance = CustomerDatabase.getInstance(application)
        val customerDatasource = databaseInstance.customerDao
        val transactionRecordDatasource = databaseInstance.transactionRecordDao
        val viewModelFactory =
            SuccessfulTransactionViewModelProviderFactory(customerDatasource, transactionRecordDatasource)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[SuccessfulTransactionViewModel::class.java]

        viewModel.initiateTransaction(
            requireContext(),
            senderCustomer,
            receiverCustomer,
            transferAmount
        )

        viewModel.navigateToCustomersScreen.observe(viewLifecycleOwner) {

            findNavController().navigate(R.id.action_successfulTransactionFragment_to_customersFragment)
        }

        viewModel.updatedSenderCustomer(senderCustomer)
        viewModel.receiverSenderCustomer(receiverCustomer)
        binding.transferredAmountTextView.text = transferAmount.toString()

        binding.lifecycleOwner = this
        binding.customerViewmodel = viewModel
        return binding.root
    }
}