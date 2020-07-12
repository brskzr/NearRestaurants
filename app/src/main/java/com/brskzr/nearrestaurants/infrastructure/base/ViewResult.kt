package com.brskzr.nearrestaurants.infrastructure.base

data class ViewResult<T>(var data: T?, val viewResultType: ViewResultType) {

}

enum class ViewResultType {
    Loading,
    Success,
    Error
}