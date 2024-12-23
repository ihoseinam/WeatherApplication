package ir.hoseinahmadi.weatherapplication.data.model.apiCall

sealed class NetWorResult<out T>(
    val data: T? = null,
    val message: String? = null
) {
    data class Success<out T>(val resData: T?) : NetWorResult<T>(resData)
    data class Error(val resMessage: String?=null) : NetWorResult<Nothing>(message = resMessage)
    data object NotCall : NetWorResult<Nothing>()
    data object Loading : NetWorResult<Nothing>()
}