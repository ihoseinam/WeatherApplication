package ir.hoseinahmadi.weatherapplication.data.model.apiCall


sealed class NetWorResult{
    data class Success(val cityName:String) : NetWorResult()
    data class Error(val resMessage: String?=null) : NetWorResult()
    data object NotCall : NetWorResult()
    data object Loading : NetWorResult()
}