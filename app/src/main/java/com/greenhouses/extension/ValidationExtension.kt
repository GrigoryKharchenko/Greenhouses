package com.greenhouses.extension

import java.util.regex.Pattern

private const val LOGIN_PATTERN = "^[\\w\\-]*\$"

fun String.isValidLogin(): Boolean =
    Pattern.compile(LOGIN_PATTERN).matcher(this).matches()
