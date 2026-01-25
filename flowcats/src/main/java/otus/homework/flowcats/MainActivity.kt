package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    private var job: Job? = null
    private lateinit var view: CatsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        job = lifecycleScope.launch {
            catsViewModel.state.collect { result ->
                when (result) {
                    is Result.Success<Fact> -> {
                        view.populate(result.data)
                    }
                    is Result.Error -> {
                        view.showToast(result.errorMsg)
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }
}