package com.paranid5.mvc_regex_spring.model

const val FULL_TAKE = -1

class SubstringRepository {
    private var matches = listOf<SubstringModel>()
    private var take = 0

    val shownMatches: List<SubstringModel>
        get() = matches.let { if (take == FULL_TAKE) it else it.take(take) }

    val totalMatches: Int
        get() = matches.size

    fun matchSubstrings(
        takeSubstrings: Int,
        regex: Regex,
        textInput: String,
    ) {
        take = takeSubstrings

        matches = regex
            .findAll(textInput)
            .flatMap(MatchResult::groupValues)
            .filter(String::isNotBlank)
            .mapIndexed { index, match -> SubstringModel(match, index) }
            .toList()
    }
}