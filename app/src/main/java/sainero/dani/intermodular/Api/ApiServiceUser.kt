package sainero.dani.intermodular.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.Users

interface ApiServiceUser {

    @GET("empleados")
    suspend fun getUsers() : List<Users>

    @GET("empleado/id/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): List<Users>

    @POST("empleado")
    suspend fun uploadUser(
        @Body user: Users
    ) : Response<Users>

    @PUT("empleado")
    suspend fun editUser(
        @Body user: Users
    ): Response<Users>

    @DELETE("empleado/id/{id}")
    suspend fun deleteUser(
        @Path("id") id:Int
    ): Response<Users>

    companion object {
        private var apiService: ApiServiceUser? = null

        fun getInstance() : ApiServiceUser {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    //Cambiar el puerto al 8080
                    .baseUrl("http://192.168.18.2:3000/api/")
                    //.baseUrl("http://192.168.1.136:3000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceUser::class.java)
            }


            return apiService!!
        }
    }
}