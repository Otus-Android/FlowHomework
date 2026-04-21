package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _catsStateFlowData = MutableStateFlow<Result>(Result.Loading)
    val catsStateFlowData: StateFlow<Result> = _catsStateFlowData

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts().catch { e ->
                _catsStateFlowData.value = Result.Error(e)
                Log.e("ApiError", e.toString())
            }
                .collect {
                    _catsStateFlowData.value = Result.Success(fact = it)
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}