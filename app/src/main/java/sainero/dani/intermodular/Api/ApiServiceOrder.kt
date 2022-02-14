package sainero.dani.intermodular.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.Pedidos

interface ApiServiceOrder {

    @GET("pedidos")
    suspend fun getOrders() : List<Pedidos>

    @GET("pedido/id/{id}")
    suspend fun getOrderById(
        @Path("id") id: Int
    ): Response<Pedidos>

    @POST("pedido")
    suspend fun uploadOrder(
        @Body order : Pedidos
    ) : Response<Pedidos>

    @PUT("pedido")
    suspend fun editOrder(
        @Body order : Pedidos
    ) : Response<Pedidos>

    @DELETE("pedido/id/{id}")
    suspend fun deleteOrder(
        @Path("id") id:Int
    ): Response<Pedidos>

    companion object {
        private var apiService: ApiServiceOrder? = null

        fun getInstance() : ApiServiceOrder {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    //Cambiar el puerto al 8080
                    .baseUrl("http://192.168.18.2:3000/api/")
                    //.baseUrl("http://192.168.1.136:3000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceOrder::class.java)
            }
            return apiService!!
        }
    }
}