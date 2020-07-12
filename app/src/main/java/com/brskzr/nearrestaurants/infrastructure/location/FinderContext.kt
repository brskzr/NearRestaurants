package com.brskzr.nearrestaurants.infrastructure.location

class FinderContext{

    val permission: Permission by lazy {
        Permission()
    }

    val location: Location by lazy {
        Location()
    }

    var successCallback: ((lat:Double, long:Double) -> Unit)? = null
    var errorCallback: ((ErrorTypes) -> Unit)? = null
}


class Location
{
    var isEnabled = false
    var lat : Double = 0.toDouble()
    var lang : Double = 0.toDouble()
}

class Permission
{
    var state: PermissionState = PermissionState.Require
}

enum class PermissionState
{
    Granted,
    Denied,
    PerminantlyDenied,
    Require
}

enum class ErrorTypes {
    LocationSettingsNotAllowed,
    LocationNotFound
}