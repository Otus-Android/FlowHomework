package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        catsViewModel.cats.onEach {
            when (it) {
                is Result.Error -> {
                    val message = when(it.error) {
                        is SocketTimeoutException -> R.string.socket_timeout
                        else -> {R.string.unknown}
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
                is Result.Success -> view.populate(it.data)
            }
        }.launchIn(lifecycleScope)
    }
}