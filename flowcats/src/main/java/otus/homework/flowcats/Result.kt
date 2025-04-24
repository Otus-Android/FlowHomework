package otus.homework.flowcats

sealed interface Result<out D> {
    data class Success<out D>(val data: D) : Result<D>
    data class Error<D>(val error: Throwable) : Result<D>
}