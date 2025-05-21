package otus.homework.flowcats

sealed class Result {
    data class Error(val message: String) : Result()
    data class Success(val fact: Fact) : Result()
    object Loading : Result()
}