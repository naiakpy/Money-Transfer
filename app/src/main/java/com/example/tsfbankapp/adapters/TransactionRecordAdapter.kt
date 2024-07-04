package com.example.tsfbankapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tsfbankapp.databinding.ItemTransactionRecordBinding
import com.example.tsfbankapp.model.TransactionRecord

class TransactionRecordAdapter(private val clickListener: TransactionRecordClickListener) :
ListAdapter<TransactionRecord, TransactionRecordAdapter.TransactionRecordViewHolder>(
DiffCallBack
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionRecordViewHolder {
        return TransactionRecordViewHolder(
            ItemTransactionRecordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionRecordViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<TransactionRecord>() {

        override fun areItemsTheSame(
            oldItem: TransactionRecord,
            newItem: TransactionRecord
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: TransactionRecord,
            newItem: TransactionRecord
        ): Boolean {
            return oldItem.transactionId == newItem.transactionId
        }
    }

    class TransactionRecordViewHolder(private var binding: ItemTransactionRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            transactionRecord: TransactionRecord,
            clickListener: TransactionRecordClickListener
        ) {
            binding.transactionRecord = transactionRecord
            binding.clicklistener = clickListener
            binding.executePendingBindings()
        }
    }
}

class TransactionRecordClickListener(val clickListener: (transactionRecord: TransactionRecord) -> Unit) {
    fun onClick(transactionRecord: TransactionRecord) = clickListener(transactionRecord)
}