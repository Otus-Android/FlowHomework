package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenStarted {
            catsViewModel.catsFlow.collect { result ->
                when (result) {
                    is Result.Success -> view.populate(result.data)
                    is Result.Error -> Toast.makeText(
                        this@MainActivity,
                        result.message,
                        Toast.LENGTH_LONG
                    ).show()

                    Result.Loading -> { /* Handle loading state if needed */
                    }
                }
            }
        }
    }
}