package com.jomy.catinfo.repository

import androidx.datastore.core.DataStore
import com.jomy.catinfo.StringList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class to store and read from datastore
 */
@Singleton
class DataStorePreferenceManager
@Inject constructor(
    private val stringListDataStore : DataStore<StringList>
) {

    fun appendStringList(breedId:String){
        CoroutineScope(Dispatchers.IO).launch {
            stringListDataStore.updateData {currentList->
                val newList = currentList.toBuilder()
                newList.addItems(breedId).build()
            }
        }
    }

    fun saveStringList(favouriteList: List<String>){
        CoroutineScope(Dispatchers.IO).launch {
            stringListDataStore.updateData {currentList->
                val newList = currentList.toBuilder()
                newList.clearItems().build()
                newList.clear().build()
                newList.addAllItems(favouriteList).build()
            }
        }
    }
    suspend fun readStringList():StringList {
        return stringListDataStore.data.first()
    }
}