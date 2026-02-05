package otus.homework.flowcats

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private val diContainer = DiContainer()

    private val catsViewModel by viewModels<CatsViewModel> {
        CatsViewModelFactory(diContainer.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.catsState.collect { result ->
                when (result) {
                    is Result.Success -> view.populate(result.data)
                    is Result.Error -> {
                        view.showError(result.exception.message ?: "Unknown error")
                    }

                    Result.Loading -> {
                        view.showLoading()
                    }
                }
            }
        }
    }
}