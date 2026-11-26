package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(result: Result<Fact>) {
        when (result) {
            is Result.Empty<*> -> findViewById<TextView>(R.id.fact_textView).text = context.getString(R.string.empty)
            is Result.Success<Fact> ->  findViewById<TextView>(R.id.fact_textView).text = result.value.text
            is Result.Error<*> -> showToast(result.cause.message)
        }
    }

    private fun showToast(text: String?) = text?.also {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }
}

interface ICatsView {

    fun populate(result: Result<Fact>)
}