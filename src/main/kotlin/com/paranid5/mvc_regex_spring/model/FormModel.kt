package com.paranid5.mvc_regex_spring.model

data class FormModel(
    var textInput: String = "",
    var regexInput: String = ".*",
    var takeInput: String = ""
)