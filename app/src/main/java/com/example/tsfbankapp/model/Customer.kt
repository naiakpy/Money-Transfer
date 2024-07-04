package com.example.tsfbankapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
@Entity(tableName = "customer_table")
data class Customer (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "customer_id")
        var id: Long = 0L,
        val customerName: String,
        val customerEmail: String,
        val customerMobileNumber: String,
        val customerAccountNumber: String,
        val swiftCode: String,
        val accountBalance: Int,
        val bankName: String,
        ) : Parcelable