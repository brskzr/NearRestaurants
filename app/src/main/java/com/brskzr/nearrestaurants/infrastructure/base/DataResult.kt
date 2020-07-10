package com.brskzr.nearrestaurants.infrastructure.base


class DataResult<T> {

    val data : T?
    val status: Status
    val errorCode: String

    constructor(data: T?, status: Status, errorCode: String) {
        this.data = data
        this.status = status
        this.errorCode = errorCode
    }

    constructor(data: T?, status: Status) : this(data, status, "")

    constructor(status: Status, message: String) : this(null, status, message)

    val hasError:Boolean
        get() = status == Status.Error

    val hasValue:Boolean
        get() = data != null

    companion object {

        fun<TResult> Success(data: TResult?) : DataResult<TResult> {
            return DataResult<TResult>(
                data,
                Status.Success
            )
        }

        fun<TResult> Error(code: String) : DataResult<TResult> {
            return DataResult<TResult>(
                Status.Error,
                code
            )
        }

        fun<TResult> Warning(warning: String) : DataResult<TResult> {
            return DataResult<TResult>(
                Status.Warning,
                warning
            )
        }

        fun<TResult> Default() : DataResult<TResult> {
            return DataResult<TResult>(
                null,
                Status.None
            )
        }
    }
}

enum class Status {
    None,
    Warning,
    Error,
    Success
}