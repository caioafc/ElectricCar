package br.caioa.electriccar.data

import br.caioa.electriccar.domain.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi {

    @GET("cars.json")
    fun getAllCars() : Call<List<Car>>
}