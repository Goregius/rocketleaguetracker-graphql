package com.github.goregius.rankinflation.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <R> flowInBatches(range: IntRange, maxBatchSize: Int, block: suspend CoroutineScope.(Int) -> R): Flow<R> = flow {
    for (startBatch in range step maxBatchSize) {
        coroutineScope {
            (startBatch until startBatch + maxBatchSize).mapNotNull { index ->
                if (index <= range.last) async { block(index) } else null
            }.awaitAll().forEach {
                emit(it)
            }
        }
    }
}