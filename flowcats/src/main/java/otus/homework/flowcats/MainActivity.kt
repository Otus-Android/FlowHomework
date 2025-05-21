package otus.homework.flowcats

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.catsLiveData.collect {
                view.findViewById<ProgressBar>(R.id.progressBar).isVisible = it is Result.Loading
                when (it) {
                    is Result.Success -> {
                        view.populate(it.fact)
                    }

                    is Result.Error -> {
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_LONG).show()
                    }

                    else -> {}
                }
            }
        }
    }
}