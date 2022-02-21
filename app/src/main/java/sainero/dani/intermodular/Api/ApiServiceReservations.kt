package sainero.dani.intermodular.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.Extras
import sainero.dani.intermodular.DataClass.Reservation

interface ApiServiceReservations {

    @GET("reservas/dia/{anyo}/{mes}/{dia}")
    suspend fun getReservationsToOneDay(
        @Path("anyo") anyo: Int,
        @Path("mes") mes: Int,
        @Path("dia") dia: Int
    ) : Response<List<Reservation>>

    @GET("reservas/minuto/{anyo}/{mes}/{dia}/{hora}/{minuto}")
    suspend fun getReservationsUseMinute(
        @Path("anyo") anyo: Int,
        @Path("mes") mes: Int,
        @Path("dia") dia: Int,
        @Path("minuto") minute: Int
    ): Response<List<Reservation>>

    @POST("reserva")
    suspend fun getReservation(
        @Body reservation: Reservation
    ): Response<Reservation>

    @DELETE("reserva/id/{id}")
    suspend fun deleteReservation(
        @Path("id") id:Int
    ): Response<Reservation>

    @PUT("reserva")
    suspend fun editReservation(
        @Body reservation: Reservation
    ) : Response<Reservation>



    companion object {
        private var apiService: ApiServiceReservations? = null

        fun getInstance() : ApiServiceReservations {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    //Cambiar el puerto al 8080
                    .baseUrl("http://192.168.18.2:3000/api/")
                    //.baseUrl("http://192.168.1.136:3000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceReservations::class.java)
            }
            return apiService!!
        }
    }
}