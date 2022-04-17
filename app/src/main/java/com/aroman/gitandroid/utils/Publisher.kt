package com.aroman.gitandroid.utils

import android.os.Handler

private class Subscriber<T>(
    private val handler: Handler,
    private val callback: (T?) -> Unit
) {
    fun invoke(value: T?) {
        handler.post { callback.invoke(value) }
    }
}

class Publisher<T>(private val isSingle: Boolean = false) {
    private val subscribers: MutableSet<Subscriber<T?>> = mutableSetOf()
    private var value: T? = null
    private var hasFirstValue = false

    fun subscribe(handler: Handler, callback: (T?) -> Unit) {
        val subscriber = Subscriber(handler, callback)
        subscribers.add(subscriber)
        if (hasFirstValue) {
            subscriber.invoke(value)
        }
    }

    fun unsubscribeAll() {
        subscribers.clear()
    }

    fun post(value: T) {
        if (!isSingle) {
            hasFirstValue = true
            this.value = value
        }
        subscribers.forEach { it.invoke(value) }
    }
}