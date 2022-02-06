package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        /*catsViewModel.catsLiveData.observe(this){
            view.populate(it)
        }*/
        lifecycleScope.launchWhenStarted {
            catsViewModel.catsStateFlow.onEach { result ->
                when (result) {
                    is Result.Success -> {
                        result.data?.let {
                            view.populate(it)
                        }
                    }
                    is Result.Error -> {
                        view.onError(result.msg)
                    }
                }
            }.launchIn(this)
        }
    }
}