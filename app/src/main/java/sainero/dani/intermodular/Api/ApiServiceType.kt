package sainero.dani.intermodular.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.Tipos

interface ApiServiceType {

    @GET("tipos")
    suspend fun getTypes() : List<Tipos>

    @GET("tipo/id/{id}")
    suspend fun getTypeById(
        @Path("id") id: Int
    ): List<Tipos>

    @POST("tipo")
    suspend fun uploadType(
        @Body tipo : Tipos
    ): Response<Tipos>

    @PUT("tipo")
    suspend fun editType(
        @Body tipo: Tipos
    ): Response<Tipos>

    @DELETE("tipo/id/{id}")
    suspend fun deleteType(
        @Path("id") id:Int
    ): Response<Tipos>

    companion object {
        private var apiService: ApiServiceType? = null

        fun getInstance() : ApiServiceType {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    //Cambiar el puerto al 8080
                    //.baseUrl("http://192.168.18.2:3000/api/")
                    .baseUrl("http://192.168.1.136:3000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceType::class.java)
            }
            return apiService!!
        }
    }
}