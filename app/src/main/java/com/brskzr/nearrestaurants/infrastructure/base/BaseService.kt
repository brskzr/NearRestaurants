package com.brskzr.nearrestaurants.infrastructure.base

import com.brskzr.nearrestaurants.infrastructure.execution.DefaultExecutionHandler
import com.brskzr.nearrestaurants.infrastructure.execution.ExecutionHandler

abstract class BaseService {

    val handler: ExecutionHandler
        get() = Companion.handler


    companion object {
        private val handler: ExecutionHandler by lazy {
            DefaultExecutionHandler()
        }
    }
}