package dev.gtcl.finastra.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.gtcl.finastra.model.Photo
import dev.gtcl.finastra.model.Repository
import kotlinx.coroutines.*


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

    private var lastJob: Job? = null

    init {
        fetchPhotos()
    }

    fun fetchPhotos(){
        if (lastJob?.isActive == true) return
        lastJob = coroutineScope.launch {
            try {
                _loading.value = true
                _photos.value = ArrayList()
                _photos.value = repository.getMarsRoverPhotos(SOL).await().photos
            } catch (e: Exception) {
                _errorMessage.value = e.toString()
            } finally {
                _loading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        lastJob?.cancel()
    }
}