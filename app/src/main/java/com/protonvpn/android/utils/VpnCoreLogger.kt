/*
 * Copyright (c) 2020 Proton Technologies AG
 *
 * This file is part of ProtonVPN.
 *
 * ProtonVPN is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ProtonVPN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ProtonVPN.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.protonvpn.android.utils

import com.protonvpn.android.logging.ApiLogError
import com.protonvpn.android.logging.ApiLogRequest
import com.protonvpn.android.logging.ApiLogResponse
import com.protonvpn.android.logging.LogCategory
import com.protonvpn.android.logging.LogLevel
import com.protonvpn.android.logging.ProtonLogger
import me.proton.core.accountmanager.domain.LogTag as AccountLogTag
import me.proton.core.humanverification.presentation.LogTag as HvLogTag
import me.proton.core.util.android.sentry.TimberLogger
import me.proton.core.crypto.common.keystore.LogTag as KeystoreLogTag
import me.proton.core.network.domain.LogTag as NetworkLogTag
import me.proton.core.util.kotlin.Logger

// Core logs full response body in debug, truncate it.
private const val MAX_DEBUG_MSG_LENGTH = 500

class VpnCoreLogger : Logger by TimberLogger {

    override fun e(tag: String, message: String) {
        TimberLogger.e(tag, message)
        forwardToProtonLogger(tag, LogLevel.ERROR, message)
    }

    override fun e(tag: String, e: Throwable) {
        TimberLogger.e(tag, e)
        forwardToProtonLogger(tag, LogLevel.ERROR, messageWithError("no message", e))
    }

    override fun e(tag: String, e: Throwable, message: String) {
        TimberLogger.e(tag, e, message)
        forwardToProtonLogger(tag, LogLevel.ERROR, messageWithError(message, e))
    }

    override fun i(tag: String, message: String) {
        TimberLogger.i(tag, message)
        forwardToProtonLogger(tag, LogLevel.INFO, message)
    }

    override fun i(tag: String, e: Throwable, message: String) {
        TimberLogger.i(tag, e, message)
        forwardToProtonLogger(tag, LogLevel.INFO, messageWithError(message, e))
    }

    override fun w(tag: String, message: String) {
        TimberLogger.w(tag, message)
        forwardToProtonLogger(tag, LogLevel.WARN, message)
    }

    override fun w(tag: String, e: Throwable) {
        TimberLogger.w(tag, e)
        forwardToProtonLogger(tag, LogLevel.WARN, messageWithError("no message", e))
    }

    override fun w(tag: String, e: Throwable, message: String) {
        TimberLogger.w(tag, e, message)
        forwardToProtonLogger(tag, LogLevel.WARN, messageWithError(message, e))
    }

    override fun d(tag: String, message: String) {
        TimberLogger.d(tag, message)
        forwardToProtonLogger(tag, LogLevel.DEBUG, message.take(MAX_DEBUG_MSG_LENGTH))
    }

    override fun d(tag: String, e: Throwable, message: String) {
        TimberLogger.d(tag, e, message)
        forwardToProtonLogger(tag, LogLevel.DEBUG, messageWithError(message, e))
    }

    override fun v(tag: String, message: String) {
        TimberLogger.v(tag, message)
        forwardToProtonLogger(tag, LogLevel.TRACE, message)
    }

    override fun v(tag: String, e: Throwable, message: String) {
        TimberLogger.v(tag, e, message)
        forwardToProtonLogger(tag, LogLevel.TRACE, messageWithError(message, e))
    }

    private fun messageWithError(message: String, e: Throwable) =
        "$message\n${e.stackTraceToString()}"

    private fun categoryForTag(tag: String) = when (tag) {
        AccountLogTag.SESSION_REFRESH -> LogCategory.API
        AccountLogTag.SESSION_REQUEST -> LogCategory.API
        AccountLogTag.SESSION_FORCE_LOGOUT -> LogCategory.API
        NetworkLogTag.SERVER_TIME_PARSE_ERROR -> LogCategory.API
        NetworkLogTag.API_REQUEST -> LogCategory.API
        NetworkLogTag.API_RESPONSE -> LogCategory.API
        NetworkLogTag.API_ERROR -> LogCategory.API
        NetworkLogTag.DEFAULT -> LogCategory.API
        HvLogTag.HV_REQUEST_ERROR -> LogCategory.API
        KeystoreLogTag.KEYSTORE_INIT -> LogCategory.SECURE_STORE
        KeystoreLogTag.KEYSTORE_ENCRYPT -> LogCategory.SECURE_STORE
        KeystoreLogTag.KEYSTORE_DECRYPT -> LogCategory.SECURE_STORE
        else -> {
            DebugUtils.debugAssert("Unknown log tag. Update this mapping.") { true }
            LogCategory.APP
        }
    }

    private fun forwardToProtonLogger(tag: String, sourceLevel: LogLevel, message: String) {
        when (tag) {
            NetworkLogTag.API_REQUEST -> ProtonLogger.log(ApiLogRequest, message)
            NetworkLogTag.API_RESPONSE -> ProtonLogger.log(ApiLogResponse, message)
            NetworkLogTag.API_ERROR -> ProtonLogger.log(ApiLogError, message)
            else -> ProtonLogger.logCustom(sourceLevel, categoryForTag(tag), message)
        }
    }
}
