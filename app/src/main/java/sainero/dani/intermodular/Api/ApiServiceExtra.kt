package sainero.dani.intermodular.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.Extras

interface ApiServiceExtra {

    @GET("extras")
    suspend fun getExtras() : List<Extras>

    @GET("extra/id/{id}")
    suspend fun getExtraById(
        @Path("id") id: Int
    ): List<Extras>

    @POST("extra")
    suspend fun uploadExtra(
        @Body extra : Extras
    ) : Response<Extras>

    @PUT("extra")
    suspend fun editExtra(
        @Body extra : Extras
    ) : Response<Extras>

    @DELETE("extra/id/{id}")
    suspend fun deleteExtra(
        @Path("id") id:Int
    ): Extras

    companion object {
        private var apiService: ApiServiceExtra? = null

        fun getInstance() : ApiServiceExtra {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    //Cambiar el puerto al 8080
                    .baseUrl("http://192.168.18.2:3000/api/")
                    //.baseUrl("http://192.168.1.136:3000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceExtra::class.java)
            }
            return apiService!!
        }
    }
}