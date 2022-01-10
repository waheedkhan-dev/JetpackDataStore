package com.codecollapse.jetpackdatastore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecollapse.jetpackdatastore.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    ViewModel() {


    fun incrementCount() {
        viewModelScope.launch {
            dataStoreRepository.incrementCounter()
        }
    }

    fun decrementCount() {
        viewModelScope.launch {
            dataStoreRepository.decrementCounter()
        }
    }

    fun getCounterValue(): Flow<Int> = dataStoreRepository.getCount

    fun setCounter(counterValue: Int) {
        viewModelScope.launch {
            dataStoreRepository.setCounter(counterValue)
        }
    }
}