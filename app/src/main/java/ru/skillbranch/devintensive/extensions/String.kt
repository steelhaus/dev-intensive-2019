package ru.skillbranch.devintensive.extensions

fun String.truncate(to: Int = 16): String {
    var truncatedString = this.trim()
    if (truncatedString.count() <= to) {
        return truncatedString
    }
    truncatedString = truncatedString.substring(0, to).trim() + "..."
    return truncatedString
}

fun String.stripHtml(): String {
    val htmlRegex = Regex("(<.*?>)|(&[^ а-я]{1,4}?;)")
    val spaceRegex = Regex(" {2,}")
    return this.replace(htmlRegex, "").replace(spaceRegex, " ")
}