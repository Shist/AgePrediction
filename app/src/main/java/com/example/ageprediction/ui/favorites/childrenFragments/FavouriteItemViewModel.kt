package com.example.ageprediction.ui.favorites.childrenFragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ageprediction.ui.repository.NamesRepository
import com.example.ageprediction.ui.roomDB.entities.favItem.FavItemDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavouriteItemViewModel : ViewModel(), KoinComponent {

    private val namesRepository: NamesRepository by inject()

    val favItemsFlow: Flow<List<FavItemDB>> = namesRepository.loadAllFavItemsFromDB()

    fun deleteFavItemsFromDB(namesList: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            namesRepository.deleteFavItemsFromDB(namesList)
        }
    }
}