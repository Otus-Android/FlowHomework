package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsState = MutableStateFlow<Result<PresentationFact>?>(null)
    val catsState = _catsState.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    catsRepository.listenForCatFacts().collect { data ->
                        _catsState.value = Result.Success(PresentationFact(data.text))
                    }
                } catch (e: Exception) {
                    _catsState.value = Result.Error(e.message ?: "Неизвестная ошибка сети")
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(CatsViewModel::class.java)) {
            return CatsViewModel(catsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}