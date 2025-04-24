package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _cats = MutableStateFlow<Result<Fact>>(Result.Success(Fact()))
    val cats: StateFlow<Result<Fact>> = _cats.asStateFlow()

    init {
        catsRepository.listenForCatFacts()
            .flowOn(Dispatchers.IO)
            .onEach {
                _cats.value = it
            }.launchIn(viewModelScope)
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}