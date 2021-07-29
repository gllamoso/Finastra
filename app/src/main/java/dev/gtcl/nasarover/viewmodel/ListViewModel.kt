package dev.gtcl.nasarover.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.gtcl.nasarover.EspressoIdlingResource
import dev.gtcl.nasarover.model.Photo
import dev.gtcl.nasarover.model.Repository
import kotlinx.coroutines.*


class ListViewModel: ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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

    fun fetchPhotos(sol: Int){
        if (lastJob?.isActive == true) return
        lastJob = coroutineScope.launch {
            try {
                EspressoIdlingResource.increment()
                _loading.value = true
                _photos.value = Repository.getMarsRoverPhotos(sol).await().photos
            } catch (e: Exception) {
                _errorMessage.value = e.toString()
            } finally {
                _loading.value = false
                EspressoIdlingResource.decrement()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        lastJob?.cancel()
    }
}