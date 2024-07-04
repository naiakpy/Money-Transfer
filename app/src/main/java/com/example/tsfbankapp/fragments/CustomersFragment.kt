package com.example.tsfbankapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tsfbankapp.adapters.CustomersAdapter
import com.example.tsfbankapp.adapters.ItemCustomerClickListener
import com.example.tsfbankapp.database.CustomerDatabase
import com.example.tsfbankapp.databinding.FragmentCustomersBinding
import com.example.tsfbankapp.viewmodels.CustomersViewModel
import com.example.tsfbankapp.viewmodels.CustomersViewModelProviderFactory

class CustomersFragment : Fragment() {

    private lateinit var binding: FragmentCustomersBinding
    private lateinit var viewModel: CustomersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCustomersBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = CustomerDatabase.getInstance(application).customerDao
        val viewModelFactory = CustomersViewModelProviderFactory(dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory)[CustomersViewModel::class.java]

        val adapter = CustomersAdapter(ItemCustomerClickListener { customer->
            val action = CustomersFragmentDirections.actionCustomersFragmentToDetailsFragment(customer)
            findNavController().navigate(action)
        })

        binding.customerRecyclerView.adapter = adapter

        viewModel.customerList.observe(viewLifecycleOwner) { customerList ->
            customerList.let {
                adapter.submitList(customerList)
            }
        }

        binding.viewmodel = viewModel
        return binding.root
    }
}