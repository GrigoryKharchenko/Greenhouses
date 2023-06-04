package com.greenhouses.extension

import java.util.regex.Pattern

private const val LOGIN_PATTERN = "^[[a-zA-Z0-9_]\\-]*\$"

fun String.isValidLogin(): Boolean =
    Pattern.compile(LOGIN_PATTERN).matcher(this).matches() && this.isNotEmpty() && this.length > 5
