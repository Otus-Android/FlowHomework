package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val factTextView: TextView by lazy { findViewById(R.id.fact_textView) }
    private val progressBar: View by lazy { findViewById(R.id.progressBar) }

    fun render(result: Result) {
        when (result) {

            is Result.Loading -> {
                progressBar.visibility = VISIBLE
                factTextView.visibility = GONE
            }

            is Result.Success -> {
                progressBar.visibility = GONE
                factTextView.visibility = VISIBLE
                factTextView.text = result.fact.text
            }

            is Result.Error -> {
                progressBar.visibility = GONE
                factTextView.visibility = VISIBLE
                factTextView.text = context.getString(R.string.error_text)
            }
        }
    }
}