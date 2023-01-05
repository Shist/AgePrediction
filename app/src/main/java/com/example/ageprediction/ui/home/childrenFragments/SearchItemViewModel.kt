package com.example.ageprediction.ui.home.childrenFragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ageprediction.ui.repository.NamesRepository
import com.example.ageprediction.ui.roomDB.entities.searchItem.SearchItemDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchItemViewModel(currName: String) : ViewModel(), KoinComponent {

    private val namesRepository: NamesRepository by inject()

    val searchItemFlow: Flow<SearchItemDB> = namesRepository.loadSearchItemByNameFromDB(currName)

    fun loadSearchItemToDB(queryString: String) {
        viewModelScope.launch {
            namesRepository.loadSearchItemByNameToDB(queryString)
        }
    }

    fun loadFavItemToDB(queryString: String) {
        viewModelScope.launch {
            namesRepository.loadFavItemByNameToDB(queryString)
        }
    }
}