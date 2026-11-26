package otus.homework.flowcats

sealed interface Result<T> {
    class Empty<T>: Result<T>
    class Success<T>(val value: T): Result<T>
    class Error<T>(val cause: Throwable): Result<T>
}