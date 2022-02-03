package sainero.dani.intermodular.Api

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
        /*
        //@Query("completeUser") completeUser: Users,
        @Query("_id") _id: Int,
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("dni") dni: String,
        @Query("phoneNumber") phoneNumber: String,
        @Query("fnac") fnac: String,
        @Query("user") user: String,
        @Query("password") password: String,
        @Query("email") email: String*/
    )

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
        @Query("completeZone") completeZone: Zonas,
    )

    @PUT("zonas")
    suspend fun editZone(
        @Query("_id") _id:Int
    ): Zonas

    @DELETE("zone/id/{id}")
    suspend fun deleteZone(
        @Path("id") id:Int
    ): Zonas



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