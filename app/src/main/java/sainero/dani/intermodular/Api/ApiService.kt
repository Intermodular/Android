package sainero.dani.intermodular.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.*

interface ApiService {

    //Empleados
    @GET("empleados")
    suspend fun getUsers() : List<Users>

    @GET("empleado/id/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): List<Users>

    @POST("empleado")
    suspend fun uploadUser(
        @Body user :PostUsers
    ) : Response<Users>

    @PUT("empleado")
    suspend fun editUser(
        @Query("_id") _id:Int,
        @Query("user") users: Users
    ): Users

    @DELETE("empleado/id/{id}")
    suspend fun deleteUser(
        @Path("id") id:Int
    ): Users


    //Productos
    @GET("productos")
    suspend fun getproducts() : List<Productos>

    @GET("producto/id/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): List<Productos>

    @POST("product")
    suspend fun uploadProduct(
        @Query("completeProduct") completeProduct: Productos,
    )

    @PUT("producto")
    suspend fun editProduct(
        @Query("_id") _id:Int
    ): Productos

    @DELETE("producto/id/{id}")
    suspend fun deleteProduct(
        @Path("id") id:Int
    ): Productos


    //Zonas

    @GET("zonas")
    suspend fun getZones() : List<Zonas>

    @GET("zona/id/{id}")
    suspend fun getZoneById(
        @Path("id") id: Int
    ): List<Zonas>

    @POST("zona")
    suspend fun uploadZone(
       @Body zone: Zonas,
    )

    @PUT("zonas")
    suspend fun editZone(
        @Query("_id") _id:Int
    ): Zonas

    @DELETE("zone/id/{id}")
    suspend fun deleteZone(
        @Path("id") id:Int
    ): Zonas

    //Extras
    @GET("extras")
    suspend fun getExtras() : List<Extras>

    @GET("extra/id/{id}")
    suspend fun getExtraById(
        @Path("id") id: Int
    ): List<Extras>

    @POST("extra")
    suspend fun uploadExtra(
        @Body extra :Extras
    )

    @PUT("extra")
    suspend fun editExtra(

    ): Extras

    @DELETE("extra/id/{id}")
    suspend fun deleteExtra(
        @Path("id") id:Int
    ): Extras

    //Mesas
    @GET("mesas")
    suspend fun getTables() : List<Mesas>

    @GET("mesa/id/{id}")
    suspend fun getTableById(
        @Path("id") id: Int
    ): List<Mesas>

    @POST("mesa")
    suspend fun uploadTable(
        @Body mesa :Mesas
    )

    @PUT("mesa")
    suspend fun editTable(

    ): Mesas

    @DELETE("mesa/id/{id}")
    suspend fun deleteTable(
        @Path("id") id:Int
    ): Mesas


    //Mesas
    @GET("tipos")
    suspend fun getTypes() : List<Tipos>

    @GET("tipo/id/{id}")
    suspend fun getTypeById(
        @Path("id") id: Int
    ): List<Tipos>

    @POST("tipo")
    suspend fun uploadType(
        @Body tipo :Tipos
    )

    @PUT("tipo")
    suspend fun editType(

    ): Tipos

    @DELETE("tipo/id/{id}")
    suspend fun deleteType(
        @Path("id") id:Int
    ): Tipos



    companion object {
       private var apiService: ApiService? = null

        fun getInstance() : ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    //Cambiar el puerto al 8080
                    .baseUrl("http://192.168.18.2:3000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }
            return apiService!!
        }




    }
}