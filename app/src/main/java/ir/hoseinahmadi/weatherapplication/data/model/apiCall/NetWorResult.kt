package ir.hoseinahmadi.weatherapplication.data.model.apiCall


sealed class NetWorResult{
    data object Success : NetWorResult()
    data class Error(val resMessage: String?=null) : NetWorResult()
    data object NotCall : NetWorResult()
    data object Loading : NetWorResult()
}