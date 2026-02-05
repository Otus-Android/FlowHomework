package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    private lateinit var textView: TextView

    override fun onFinishInflate() {
        super.onFinishInflate()
        textView = findViewById(R.id.fact_textView)
    }

    override fun populate(fact: Fact) {
        textView.text = fact.text
        textView.setTextColor(context.getColor(android.R.color.black))
    }

    fun showError(errorMessage: String) {
        textView.text = "Error: $errorMessage"
        textView.setTextColor(context.getColor(android.R.color.holo_red_dark))
    }

    fun showLoading() {
        textView.text = "Loading..."
        textView.setTextColor(context.getColor(android.R.color.darker_gray))
    }
}

interface ICatsView {

    fun populate(fact: Fact)
}