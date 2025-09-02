package otus.homework.flowcats

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onEach


class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsFlow = MutableStateFlow<Result>(Result.Initial)
    val catsFlow: StateFlow<Result> = _catsFlow.asStateFlow()

    init {
//        viewModelScope.launch {
             catsRepository.listenForCatFacts()
                 .onEach {_catsFlow.value = Result.Success(it)}
                 .catch { _catsFlow.value = Result.Error(it.message.toString()) }
                 .launchIn(viewModelScope)

                }
        }





class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(CatsRepository::class.java).newInstance(catsRepository)
}