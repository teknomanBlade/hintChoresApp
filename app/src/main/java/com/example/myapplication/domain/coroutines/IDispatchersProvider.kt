package com.example.myapplication.domain.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatchersProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}