package com.paranid5.mvc_regex_spring

import com.paranid5.mvc_regex_spring.model.FULL_TAKE
import com.paranid5.mvc_regex_spring.model.FormModel
import com.paranid5.mvc_regex_spring.model.SubstringRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

private val ENGLISH_OR_DIGITS = Regex("[a-zA-Zа-яА-Я0-9,.;\\-\\s]*")

@Controller
class AppController {
    @Autowired
    private lateinit var repository: SubstringRepository

    @Autowired
    private lateinit var view: SubstringView

    @Volatile
    private var hasErrorInInput: Boolean = false

    @GetMapping("/")
    fun matches(model: Model): String {
        model.addAttribute("form_model", FormModel())
        return "matches"
    }

    @GetMapping("/matched")
    fun matched(
        @RequestParam textInput: String,
        @RequestParam regexInput: String,
        @RequestParam takeInput: String,
        model: Model
    ): String {
        val input = textInput.takeIf(::validateTextInput) ?: return "input_error"
        val regex = regexInput.takeIf(::validateRegexInput)?.let(::Regex) ?: return "input_error"
        val take = takeInput.takeIf(::validateTakeInput)?.let(::parseTakeInput) ?: return "input_error"

        repository.matchSubstrings(take, regex, input)
        model.addAttribute("repository", repository)
        model.addAttribute("view", view)
        return "matched"
    }

    @GetMapping("/input_error")
    fun inputError(): String = "input_error"

    private fun validateTextInput(text: String): Boolean =
        validTextInput(text).also {
            hasErrorInInput = updatedErrorState(it)
        }

    private fun validateRegexInput(regex: String): Boolean =
        validRegexInput(regex).also {
            hasErrorInInput = updatedErrorState(it)
        }

    private fun validateTakeInput(take: String): Boolean =
        validTakeInput(take).also {
            hasErrorInInput = updatedErrorState(it)
        }

    private fun updatedErrorState(isErrorInInput: Boolean): Boolean =
        if (isErrorInInput) hasErrorInInput else false
}

private fun validTextInput(text: String): Boolean =
    text matches ENGLISH_OR_DIGITS

private fun validRegexInput(regex: String): Boolean =
    runCatching { Regex(regex) }
        .map { true }
        .getOrDefault(false)

private fun validTakeInput(take: String): Boolean =
    when {
        take.isBlank() -> true
        else -> take.toIntOrNull()?.takeIf { it > 0 } != null
    }

private fun parseTakeInput(take: String): Int =
    when {
        take.isBlank() -> null ?: FULL_TAKE
        else -> take.toIntOrNull()?.takeIf { it > 0 } ?: FULL_TAKE
    }