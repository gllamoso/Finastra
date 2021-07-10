package dev.gtcl.finastra.model

open class Repository {

    fun getMarsRoverPhotos(sol: Int) =
        NasaApi.retrofitService.getMarsRoverPhotos(sol)

    companion object {
        fun getInstance() = RepositoryInstance
    }
}

object RepositoryInstance: Repository()