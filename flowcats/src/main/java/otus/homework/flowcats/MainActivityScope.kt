package otus.homework.flowcats

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class MainActivityScope : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = CoroutineName("CatsCoroutine") + Dispatchers.Main
}
