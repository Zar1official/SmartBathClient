package ru.zar1official.smartbathclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _percentage = MutableLiveData<Int>().apply { value = 0 }
    val percentage: LiveData<Int> = _percentage

    fun onChangePercentage() {
        val value = percentage.value!!
        _percentage.value = value + 1
    }

}