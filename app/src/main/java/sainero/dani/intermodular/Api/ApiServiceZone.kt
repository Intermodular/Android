package sainero.dani.intermodular.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.Zonas

interface ApiServiceZone {

    @GET("zonas")
    suspend fun getZones() : List<Zonas>

    @GET("zona/id/{id}")
    suspend fun getZoneById(
        @Path("id") id: Int
    ): List<Zonas>

    @POST("zona")
    suspend fun uploadZone(
        @Body zone: Zonas,
    ): Response<Zonas>

    @PUT("zonas")
    suspend fun editZone(
        @Body zone: Zonas
    ): Response<Zonas>

    @DELETE("zone/id/{id}")
    suspend fun deleteZone(
        @Path("id") id:Int
    ): Response<Zonas>

    companion object {
        private var apiService: ApiServiceZone? = null

        fun getInstance() : ApiServiceZone {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    //Cambiar el puerto al 8080
                    //.baseUrl("http://192.168.18.2:3000/api/")
                    .baseUrl("http://192.168.1.136:3000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceZone::class.java)
            }
            return apiService!!
        }
    }
}