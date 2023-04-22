package ru.yurii.testingworkshopapp.utils.extensions

import kotlin.coroutines.cancellation.CancellationException

val <T> T.exhaustive: T
    get() = this

suspend fun <T> runCatchingNonCancellation(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (cancellation: CancellationException) {
        throw cancellation
    } catch (e: Exception) {
        Result.failure(e)
    }
}
