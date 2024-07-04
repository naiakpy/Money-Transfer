package com.example.tsfbankapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tsfbankapp.databinding.ItemCustomerBinding
import com.example.tsfbankapp.model.Customer

class CustomersAdapter(private val clickListener: ItemCustomerClickListener) :
ListAdapter<Customer, CustomersAdapter.CustomerViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            ItemCustomerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }


    companion object DiffCallBack : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class CustomerViewHolder(private var binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(customer: Customer, clickListener: ItemCustomerClickListener) {
            binding.customer = customer
            binding.clicklistener = clickListener
            binding.executePendingBindings()
        }
    }
}

class ItemCustomerClickListener(val clickListener: (customer: Customer) -> Unit) {
    fun onClick(customer: Customer) = clickListener(customer)
}