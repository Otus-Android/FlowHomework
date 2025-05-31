package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsData = MutableStateFlow<Result>(Result.Empty)
    val catsData: StateFlow<Result> = _catsData.asStateFlow()

    fun init() {
        viewModelScope.launch {
            try {
                catsRepository.listenForCatFacts().collect { fact ->
                    _catsData.value = Result.Success(fact)
                }
            } catch (e: Exception) {
                _catsData.value = Result.Error("Error: " + e.message)
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class Result() {
    data class Error(val message: String) : Result()
    data class Success<T>(val result: T) : Result()
    object Empty : Result()
}