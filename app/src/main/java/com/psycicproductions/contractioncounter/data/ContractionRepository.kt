package com.psycicproductions.contractioncounter.data

import androidx.lifecycle.LiveData

class ContractionRepository(private val contractionDao: ContractionDao) {
    val allContractions: LiveData<List<Contraction>> = contractionDao.getAllContractions()
    val currentContraction: LiveData<Contraction?> = contractionDao.getCurrentContraction()

    suspend fun insert(contraction: Contraction) {
        contractionDao.insertContraction(contraction)
    }

    suspend fun update(contraction: Contraction) {
        contractionDao.updateContraction(contraction)
    }

    suspend fun delete(contraction: Contraction) {
        contractionDao.deleteContraction(contraction)
    }

    suspend fun getContractionById(id: Int): Contraction? {
        return contractionDao.getContractionById(id)
    }

    suspend fun deleteAllContractions() {
        contractionDao.deleteAllContractions()
    }

    suspend fun endOpenContraction(contractionEndDt: Long) {
        contractionDao.endOpenContraction(contractionEndDt)
    }
}