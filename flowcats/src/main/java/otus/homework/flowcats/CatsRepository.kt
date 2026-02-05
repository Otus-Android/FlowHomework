package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {
    fun listenForCatFacts() = flow<Result<Fact>> {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                emit(Result.Error(e))
            }
            delay(refreshIntervalMs)
        }
    }
}