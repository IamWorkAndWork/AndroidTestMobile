package app.practice.question5.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val formatYMD = SimpleDateFormat("yyyy-MM-dd")
    private val formatApp = SimpleDateFormat("D MMM yyyy")

    fun stringToDate(date: String): Date? {
//        val mDateTime = formatYMD.parse(date)//getDateTimeFormat(dbFormat, context)?.parse(dateStr);
        try {
            return formatYMD.parse(date)
        } catch (e: Exception) {
            return Date()
        }
    }

    fun stringToDBFormat(date: String): String {
        return formatYMD.format(stringToDate(date))
    }

    fun dateDBToAppFormat(dbDate: String): String {
        val date = stringToDate(dbDate)
        return formatApp.format(date)
    }

}