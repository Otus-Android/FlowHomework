package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow<Result<Fact>> {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(Result.Success(latestNews))
            delay(refreshIntervalMs)
        }
    }.flowOn(Dispatchers.IO)
        .catch { e ->
            Log.d(this@CatsRepository.javaClass.canonicalName, "Error", e)
            emit(Result.Error(e.message ?: "Error"))
        }
}