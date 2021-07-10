package dev.gtcl.finastra.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.gtcl.finastra.model.Photo
import dev.gtcl.finastra.model.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


const val SOL = 1000
class ListViewModel: ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val repository = Repository.getInstance()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        fetchPhotos()
    }

    fun fetchPhotos(){
        coroutineScope.launch {
            try {
                _loading.value = true
                _photos.value = ArrayList()
                val test = repository.getMarsRoverPhotos(SOL).await().photos
                _photos.value = test
            } catch (e: Exception) {
                _errorMessage.value = e.toString()
            } finally {
                _loading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.complete()
    }
}