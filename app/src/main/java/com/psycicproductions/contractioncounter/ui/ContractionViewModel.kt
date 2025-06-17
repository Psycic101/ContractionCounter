package com.psycicproductions.contractioncounter.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.psycicproductions.contractioncounter.data.Contraction
import com.psycicproductions.contractioncounter.data.ContractionDatabase
import com.psycicproductions.contractioncounter.data.ContractionRepository
import kotlinx.coroutines.launch

class ContractionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContractionRepository

    val allContractions: LiveData<List<Contraction>>

    val currentContraction: LiveData<Contraction?>

    init {
        val contractionDao = ContractionDatabase.getDatabase(application).contractionDao()
        repository = ContractionRepository(contractionDao)
        allContractions = repository.allContractions
        currentContraction = repository.currentContraction
    }

    fun insert(contraction: Contraction) = viewModelScope.launch {
        repository.insert(contraction)
    }

    fun update(contraction: Contraction) = viewModelScope.launch {
        repository.update(contraction)
    }

    fun delete(contraction: Contraction) = viewModelScope.launch {
        repository.delete(contraction)
    }

    suspend fun getContractionById(id: Int): Contraction? {
        return repository.getContractionById(id)
    }

    fun deleteAllContractions() = viewModelScope.launch {
        repository.deleteAllContractions()
    }

    fun endOpenContraction(contractionEndDt: Long) = viewModelScope.launch {
        repository.endOpenContraction(contractionEndDt)
    }
}