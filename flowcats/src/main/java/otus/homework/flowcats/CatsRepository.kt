package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            try {
                val response = catsService.getCatFact()
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        emit(Result.Success(fact = it))
                    } ?: throw Exception("Unknown")
                } else {
                    throw Exception("Unknown")
                }
            } catch (e: Exception) {
                emit(Result.Error(error = e))
            }
            delay(refreshIntervalMs)
        }
    }
}