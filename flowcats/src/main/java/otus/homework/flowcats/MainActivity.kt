package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launchWhenCreated {
            catsViewModel.state.collect {
                when (it) {
                    is Result.Empty -> showToast("Empty Data")
                    is Result.Success -> view.populate(it.fact)
                    is Result.Error -> showToast(it.errorMessage)
                }
            }
        }
    }

    private fun showToast(message: String?) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}