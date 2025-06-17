package com.psycicproductions.contractioncounter.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContractionDao {
    @Query("SELECT * FROM contractions ORDER BY contractionStartDt DESC")
    fun getAllContractions(): LiveData<List<Contraction>>

    @Insert
    suspend fun insertContraction(contraction: Contraction)

    @Update
    suspend fun updateContraction(contraction: Contraction)

    @Delete
    suspend fun deleteContraction(contraction: Contraction)

    @Query("SELECT * FROM contractions WHERE id = :id")
    suspend fun getContractionById(id: Int): Contraction?

    @Query("DELETE FROM contractions")
    suspend fun deleteAllContractions()

    @Query("UPDATE contractions SET contractionEndDt = :contractionEndDt WHERE contractionEndDt IS NULL")
    suspend fun endOpenContraction(contractionEndDt: Long)

    @Query("SELECT * FROM contractions WHERE contractionEndDt IS NULL")
    fun getCurrentContraction(): LiveData<Contraction?>
}