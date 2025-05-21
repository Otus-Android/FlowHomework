package otus.homework.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface SampleRepository {

    fun produceNumbers(): Flow<Int> = flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    fun produceColors(): Flow<String> =
        flowOf("Red", "Orange", "Yellow", "Green", "Blue", "Violet", "Black", "White", "Gray")

    fun produceForms(): Flow<String> =
        flowOf("Triangle", "Circle", "Square", "Rectangle", "Oval", "Rhombus")

    fun completed()
}