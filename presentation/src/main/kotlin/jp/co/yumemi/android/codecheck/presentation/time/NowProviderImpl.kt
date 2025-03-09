package jp.co.yumemi.android.codecheck.presentation.time

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class NowProviderImpl : NowProvider {
    override fun localTimeNow(): LocalTime {
        return LocalTime.now()
    }

    override fun localDateNow(): LocalDate {
        return LocalDate.now()
    }

    override fun localDateTimeNow(): LocalDateTime {
        return LocalDateTime.now()
    }
}
