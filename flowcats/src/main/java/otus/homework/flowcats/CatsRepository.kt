package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            if (latestNews.isSuccessful && latestNews.body() != null)
                emit(Result.Success(latestNews.body()!!))
            else emit(Result.Error(latestNews.message()))
            delay(refreshIntervalMs)
        }
    }.onStart {
        emit(Result.Loading)
    }.catch { error ->
            emit(
                Result.Error(
                    error.message ?: error.localizedMessage
                    ?: "Непредвиденная ошибка. Обратитесь к разработчикам"
                )
            )
        }
}