package com.example.tsfbankapp.database

import androidx.room.*
import com.example.tsfbankapp.model.TransactionRecord

@Dao
interface TransactionRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionRecord)

    @Query("SELECT * FROM transaction_record")
    suspend fun getAllTransaction(): List<TransactionRecord>

    @Delete
    suspend fun deleteTransactionRecord(transaction: TransactionRecord)

    @Query("DELETE FROM transaction_record")
    suspend fun deleteAllTransaction()
}