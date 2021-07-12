package dev.gtcl.finastra.model

open class Repository private constructor(){

    fun getMarsRoverPhotos(sol: Int) =
        NasaApi.retrofitService.getMarsRoverPhotos(sol)

    companion object : Repository()
}