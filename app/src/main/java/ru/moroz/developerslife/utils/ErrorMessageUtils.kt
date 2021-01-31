package ru.moroz.developerslife.utils

import com.google.gson.JsonSyntaxException
import ru.moroz.developerslife.R
import ru.moroz.developerslife.throwables.NoDataThrowable
import ru.moroz.developerslife.throwables.NoMoreDataThrowable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Exception to string mapper
 */
@Singleton
class ErrorMessageUtils @Inject constructor(private val resourceProvider: ResourceProvider) {

    /**
     * Get error text
     *
     * @param throwable for error text
     * @return error text
     */
    fun getErrorMessage(throwable: Throwable): String {
        val errorId = getErrorForThrowable(throwable)
        return resourceProvider.getString(errorId)
    }

    private fun getErrorForThrowable(throwable: Throwable): Int {
        return when (throwable) {
            is UnknownHostException -> R.string.error_no_internet_connection
            is ConnectException -> R.string.error_no_internet_connection
            is SocketTimeoutException -> R.string.error_slow_internet_connection
            is JsonSyntaxException -> R.string.error_data_parsing
            is UnknownError -> R.string.error_server
            is NoMoreDataThrowable -> R.string.error_no_more_data
            is NoDataThrowable -> R.string.error_no_data
            else -> R.string.error_unknown
        }
    }
}