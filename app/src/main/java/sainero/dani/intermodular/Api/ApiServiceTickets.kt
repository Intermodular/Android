package sainero.dani.intermodular.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import sainero.dani.intermodular.DataClass.Productos
import sainero.dani.intermodular.DataClass.Ticket

interface ApiServiceTickets {

    @GET("tickets")
    suspend fun getTickets() : List<Ticket>

    @GET("ticket/id/{id}")
    suspend fun getTicketById(
        @Path("id") id: Int
    ): Response<Ticket>

    @POST("ticket")
    suspend fun uploadTicket(
        @Body product: Ticket,
    ) : Response<Ticket>

    @PUT("ticket")
    suspend fun editTicket(
        @Body ticket: Ticket
    ): Response<Ticket>

    @DELETE("ticket/id/{id}")
    suspend fun deleteTicket(
        @Path("id") id:Int
    ): Response<Ticket>

    companion object {
        private var apiService: ApiServiceTickets? = null

        fun getInstance() : ApiServiceTickets {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    //Cambiar el puerto al 8080
                    .baseUrl("http://192.168.18.2:3000/api/")
                    //.baseUrl("http://192.168.1.136:3000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServiceTickets::class.java)
            }
            return apiService!!
        }
    }
}