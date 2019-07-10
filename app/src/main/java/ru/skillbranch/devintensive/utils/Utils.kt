package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.models.User
import java.util.*
import kotlin.collections.HashMap

object Utils {
    fun parseFullName(fullName: String?) : Pair<String?, String?> {

        var parts: List<String>? = fullName?.split(" ")
        parts = parts?.filter { !it.isNullOrBlank() }
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {

        fun transliteratedStringFrom(char: Char): String {
            val isUppercased = char.isUpperCase()
            val toTransformChar = when {isUppercased -> char.toLowerCase() else -> char}
            var transformedChar = LITERA_MAP.getOrElse(toTransformChar){toTransformChar.toString()}
            return when {isUppercased -> transformedChar.capitalize() else -> transformedChar}
        }

        var transliteraBuffer = StringBuffer()
        payload.forEach {
            if (it === ' ') {
                transliteraBuffer.append(divider)
            } else {
                transliteraBuffer.append(transliteratedStringFrom(it))
            }
        }

        return transliteraBuffer.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var initials = firstName?.trimStart()?.getOrNull(0)?.toString()?.toUpperCase() ?: ""
        initials += lastName?.trimStart()?.getOrNull(0)?.toString()?.toUpperCase() ?: ""
        return when {
            initials.isNullOrBlank() -> null
            else -> initials
        }
    }



    val LITERA_MAP: HashMap<Char, String> = hashMapOf(
    'а' to "a",
    'б' to "b",
    'в' to "v",
    'г' to "g",
    'д' to "d",
    'е' to "e",
    'ё' to "e",
    'ж' to "zh",
    'з' to "z",
    'и' to "i",
    'й' to "i",
    'к' to "k",
    'л' to "l",
    'м' to "m",
    'н' to "n",
    'о' to "o",
    'п' to "p",
    'р' to "r",
    'с' to "s",
    'т' to "t",
    'у' to "u",
    'ф' to "f",
    'х' to "h",
    'ц' to "c",
    'ч' to "ch",
    'ш' to "sh",
    'щ' to "sh'",
    'ъ' to "",
    'ы' to "i",
    'ь' to "",
    'э' to "e",
    'ю' to "yu",
    'я' to "ya" )
}