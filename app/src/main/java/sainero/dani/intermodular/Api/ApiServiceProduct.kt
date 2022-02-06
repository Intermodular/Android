package sainero.dani.intermodular.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.Productos

interface ApiServiceProduct {

    @GET("productos")
    suspend fun getproducts() : List<Productos>

    @GET("producto/id/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Response<Productos>

    @POST("producto")
    suspend fun uploadProduct(
        @Body product: Productos,
    ) : Response<Productos>

    @PUT("producto")
    suspend fun editProduct(
        @Body product: Productos
    ): Response<Productos>

    @DELETE("producto/id/{id}")
    suspend fun deleteProduct(
        @Path("id") id:Int
    ): Response<Productos>

    companion object {
        private var apiService: ApiServiceProduct? = null

        fun getInstance() : ApiServiceProduct {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    //Cambiar el puerto al 8080
                    //.baseUrl("http://192.168.18.2:3000/api/")
                    .baseUrl("http://192.168.1.129:3000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceProduct::class.java)
            }
            return apiService!!
        }
    }
}