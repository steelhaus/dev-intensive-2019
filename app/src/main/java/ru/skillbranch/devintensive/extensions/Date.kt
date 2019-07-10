package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val dateInterval = abs(this.time - date.time)
    val inFuture = this.time >= date.time
    var shouldAddPrefixOfPostfix = true

    var humanizeInterval = when {
        dateInterval <= SECOND -> {
            shouldAddPrefixOfPostfix = false
            "только что"
        }
        dateInterval <= SECOND * 45 -> "несколько секунд"
        dateInterval <= SECOND * 75 -> "минуту"
        dateInterval <= MINUTE * 45 -> TimeUnits.MINUTE.plural((dateInterval / MINUTE).toInt())
        dateInterval <= MINUTE * 75 -> "час"
        dateInterval <= HOUR * 22 -> TimeUnits.HOUR.plural((dateInterval / HOUR).toInt())
        dateInterval <= HOUR * 26 -> "день"
        dateInterval <= DAY * 360 -> TimeUnits.DAY.plural((dateInterval / DAY).toInt())
        else -> {
            shouldAddPrefixOfPostfix = false
            if (inFuture) "более чем через год" else "более года назад"
        }
    }

    if (shouldAddPrefixOfPostfix) {
        val prefix = when(inFuture) {true -> "через " else -> ""}
        val postfix = when(inFuture) {true -> "" else -> " назад"}
        humanizeInterval = "$prefix$humanizeInterval$postfix"
    }
    return humanizeInterval
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    private val secondsPlurals = mapOf(PluralType.ONE to "секунду", PluralType.FEW to "секунды", PluralType.MANY to "секунд")
    private val minutesPlurals = mapOf(PluralType.ONE to "минуту", PluralType.FEW to "минуты", PluralType.MANY to "минут")
    private val hoursPlurals = mapOf(PluralType.ONE to "час", PluralType.FEW to "часа", PluralType.MANY to "часов")
    private val daysPlurals = mapOf(PluralType.ONE to "день", PluralType.FEW to "дня", PluralType.MANY to "дней")

    private fun getUnitPlurals() : Map<PluralType, String> {
        return when (this) {
            SECOND -> secondsPlurals
            MINUTE -> minutesPlurals
            HOUR -> hoursPlurals
            DAY -> daysPlurals
        }
    }

    private fun getPluralTypeFor(value: Int) : PluralType {
        val amount = abs(value) % 100
        return when (amount) {
            0 -> PluralType.MANY
            1 -> PluralType.ONE
            in 2..4 -> PluralType.FEW
            in 5..20 -> PluralType.MANY
            else -> getPluralTypeFor(amount % 10)
        }
    }

    fun plural(value: Int) : String {
        val pluralType = getPluralTypeFor(value)
        return "$value ${getUnitPlurals()[pluralType]}"
    }

    enum class PluralType {
        ONE,
        FEW,
        MANY;
    }
}