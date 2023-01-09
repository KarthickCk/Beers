package com.app.jumpingmind.data.api

import com.app.jumpingmind.domain.model.Beer
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApi {
    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int
    ): List<Beer>
}