package com.psycicproductions.contractioncounter.data

import androidx.lifecycle.LiveData

class ContractionRepository(private val contractionDao: ContractionDao) {
    val allContractions: LiveData<List<Contraction>> = contractionDao.getAllContractions()

    suspend fun insert(contraction: Contraction) {
        contractionDao.insertContraction(contraction)
    }

    suspend fun update(contraction: Contraction) {
        contractionDao.updateContraction(contraction)
    }

    suspend fun delete(contraction: Contraction) {
        contractionDao.deleteContraction(contraction)
    }
}