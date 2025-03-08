package jp.co.yumemi.android.codecheck

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface NowProvider {
    fun localTimeNow(): LocalTime
    fun localDateNow(): LocalDate
    fun localDateTimeNow(): LocalDateTime
}
