package otus.homework.flowcats

import retrofit2.http.GET
import retrofit2.Response

interface CatsService {

    @GET("fact")
    suspend fun getCatFact(): Response<Fact>
}