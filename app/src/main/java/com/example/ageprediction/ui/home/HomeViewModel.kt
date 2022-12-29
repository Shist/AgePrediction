package com.example.ageprediction.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ageprediction.ui.retrofit.AgifyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val _currAge = MutableLiveData<String>()
    val currAge: LiveData<String> = _currAge

    private val service: AgifyApi by inject()

    fun getAge(queryString: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val ageJson = service.getAgeByName(queryString)
            _currAge.value = ageJson.age.toString()
        }
    }
}