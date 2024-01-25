package com.paranid5.mvc_regex_spring

import com.paranid5.mvc_regex_spring.model.SubstringModel

class SubstringView {
    fun SubstringView(substringModel: SubstringModel) =
        "${substringModel.index + 1}. ${substringModel.value}"

    fun TotalMatchesLabel(totalMatches: Int) =
        "Total matches: $totalMatches"
}