package sainero.dani.intermodular.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import sainero.dani.intermodular.DataClass.*

interface ApiService {


    @GET("empleados")
    suspend fun getUsers(): List<Users>

    companion object {
        var apiService: ApiService? = null


        fun getInstance() : ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("http://localhost:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }
            return apiService!!
        }




    }
}