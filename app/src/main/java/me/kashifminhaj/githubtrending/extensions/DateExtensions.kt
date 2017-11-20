package me.kashifminhaj.githubtrending.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by kashif on 20/11/17.
 */
fun Date.toLastWeek(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DATE, -7)
    return cal.time
}

fun Date.toYesterday(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DATE, -1)
    return cal.time
}

fun Date.toLastMonth(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.MONTH, -1)
    return cal.time
}

fun Date.asFormat(format: String): String {
    val formatter = SimpleDateFormat(format)
    return formatter.format(this)
}