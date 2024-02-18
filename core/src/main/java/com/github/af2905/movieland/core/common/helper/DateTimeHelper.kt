package com.github.af2905.movieland.core.common.helper

object DateTimeHelper {

    internal fun getMinutesDifference(currentTime: Long, timeStamp: Long): Long? =
        getTimeDifference(currentTime, timeStamp)[DateTimeType.MINUTES]

    internal fun getHoursDifference(currentTime: Long, timeStamp: Long): Long? =
        getTimeDifference(currentTime, timeStamp)[DateTimeType.HOURS]

    private fun getTimeDifference(currentTime: Long, timeStamp: Long): Map<DateTimeType, Long> {
        val milliseconds = currentTime - timeStamp
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days.div(7)
        return mapOf(
            DateTimeType.DAYS to days,
            DateTimeType.HOURS to hours,
            DateTimeType.MINUTES to minutes,
            DateTimeType.SECONDS to seconds,
            DateTimeType.WEEKS to weeks
        )
    }

    enum class DateTimeType {
        DAYS, HOURS, MINUTES, SECONDS, WEEKS
    }
}


