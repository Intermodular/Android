package sainero.dani.intermodular.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.*

interface ApiService {


    @GET("empleados")
    suspend fun getUsers() : List<Users>

    @GET("empleado/id/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): Users
    @POST("empleado")
    suspend fun uploadUser(): Users

    @PUT("empleado")
    suspend fun editUser(): Users
    @DELETE("empleado/id/{id}")
    suspend fun deleteUser(
        @Path("id") id:Int
    ): Users

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