package com.brskzr.nearrestaurants.infrastructure.execution

import android.util.Log
import com.brskzr.nearrestaurants.infrastructure.base.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.lang.Exception


abstract class ExecutionHandler {

    protected fun scope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    abstract fun<TResult> run(execution: () -> TResult) : Deferred<DataResult<TResult>>
}

class DefaultExecutionHandler : ExecutionHandler() {
    override fun <TResult> run(execution: () -> TResult): Deferred<DataResult<TResult>> {
        return  scope().async {
            try {
                val result = execution.invoke()
                DataResult.Success(
                    result
                )
            }
            catch (ex: Exception){
                //Logging
                Log.e("AppError", ex.message + ex.toString())

                DataResult.Error<TResult>(
                    ""
                )
            }
        }

    }


}