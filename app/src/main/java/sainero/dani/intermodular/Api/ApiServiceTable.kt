package sainero.dani.intermodular.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.Mesas

interface ApiServiceTable {

    @GET("mesas")
    suspend fun getTables() : Response<List<Mesas>>

    @GET("mesa/id/{id}")
    suspend fun getTableById(
        @Path("id") id: Int
    ): Response<Mesas>

    @POST("mesa")
    suspend fun uploadTable(
        @Body mesa : Mesas
    ): Response<Mesas>

    @PUT("mesa")
    suspend fun editTable(
        @Body mesa: Mesas
    ): Response<Mesas>

    @DELETE("mesa/id/{id}")
    suspend fun deleteTable(
        @Path("id") id:Int
    ): Response<Mesas>

    companion object {
        private var apiService: ApiServiceTable? = null

        fun getInstance() : ApiServiceTable {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    //Cambiar el puerto al 8080
                    //.baseUrl("http://192.168.18.2:3000/api/")
                    .baseUrl("http://192.168.1.136:3000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceTable::class.java)
            }
            return apiService!!
        }
    }
}